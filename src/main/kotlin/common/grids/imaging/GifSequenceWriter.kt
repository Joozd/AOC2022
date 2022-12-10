package common.grids.imaging

import java.awt.image.RenderedImage
import java.io.Closeable
import java.io.File
import java.io.IOException
import javax.imageio.*
import javax.imageio.metadata.IIOMetadata
import javax.imageio.metadata.IIOMetadataNode
import javax.imageio.stream.FileImageOutputStream

class GifSequenceWriter(out: String, imageType: Int, delay: Int, loop: Boolean): Closeable {
    private val writer: ImageWriter = ImageIO.getImageWritersBySuffix("gif").next().apply{
        output = FileImageOutputStream(File(out))
        prepareWriteSequence(null)
    }
    private val params: ImageWriteParam = writer.defaultWriteParam
    private val metadata: IIOMetadata = writer.getDefaultImageMetadata(ImageTypeSpecifier.createFromBufferedImageType(imageType), params).apply{
        val root = getAsTree(nativeMetadataFormatName) as IIOMetadataNode
        val graphicsControlExtensionNode = getNode(root, "GraphicControlExtension")
        graphicsControlExtensionNode.setAttribute("disposalMethod", "none")
        graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE")
        graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE")
        graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delay / 10))
        graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0")
        getNode(root, "CommentExtensions").setAttribute("CommentExtension", "Joozd made this")
        val child = IIOMetadataNode("ApplicationExtension").apply {
            setAttribute("applicationID", "NETSCAPE")
            setAttribute("authenticationCode", "2.0")
            val loopContinuously = if (loop) 0 else 1
            userObject = byteArrayOf(0x1, (loopContinuously and 0xFF).toByte(), (loopContinuously shr 8 and 0xFF).toByte())
        }
        getNode(root, "ApplicationExtensions").appendChild(child)
        setFromTree(nativeMetadataFormatName, root)
    }

    @Throws(IOException::class)
    fun writeToSequence(img: RenderedImage?) {
        writer.writeToSequence(IIOImage(img, null, metadata), params)
    }

    @Throws(IOException::class)
    override fun close() {
        writer.endWriteSequence()
    }

    companion object {
        private fun getNode(rootNode: IIOMetadataNode, nodeName: String): IIOMetadataNode {
            (0 until rootNode.length).forEach{ i ->
                if (rootNode.item(i).nodeName.equals(nodeName, ignoreCase = true)) {
                    return rootNode.item(i) as IIOMetadataNode
                }
            }
            val node = IIOMetadataNode(nodeName)
            rootNode.appendChild(node)
            return node
        }
    }
}