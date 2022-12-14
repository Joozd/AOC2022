package common.grids.imaging

import common.grids.CoordinateWithValue
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class PNGMap<T>(private val map: Collection<CoordinateWithValue<T>>, private val scale: Int = 5, private var definedColors: ColorMaker<T>? = ColorMaker { 0xFFFFFF }) {
    fun defineColors(colorMaker: ColorMaker<T>){
        definedColors = colorMaker
    }

    /**
     * Save the image made by this PNGMap to a file
     * @param filename: The name of the file to be saved (e.g. "c:\\temp\\1.png")
     */
    fun saveImage(filename: String){
        val dimensions = DrawingArea(map)
        val image = BufferedImage(dimensions.width + 1, dimensions.height + 1, BufferedImage.TYPE_INT_RGB)
        draw(image, dimensions)
        val resizedImage = scale(image, scale)
        ImageIO.write(resizedImage, "PNG", File(filename))
    }

    fun makeBufferedImage(): BufferedImage{
        val dimensions = DrawingArea(map)
        val image = BufferedImage(dimensions.width + 1, dimensions.height + 1, BufferedImage.TYPE_INT_RGB)
        draw(image, dimensions)
        return scale(image, scale)
    }

    // Draw the map onto the image
    private fun draw(image: BufferedImage, dimensions: DrawingArea){
        map.forEach {
            val x = it.x + dimensions.offsetX
            val y = it.y + dimensions.offsetY
            val color = definedColors?.getColor(it) ?: 0xFFFFFF
            image.setRGB(x,y,color)
        }
    }

    // Scale the image 1:[scale]
    private fun scale(image: BufferedImage, scale: Int): BufferedImage{
        val w = image.width * scale
        val h = image.height * scale
        val newImage = BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
        (0 until w).forEach { x->
            (0 until h).forEach { y ->
                newImage.setRGB(x,y,image.getRGB(x/scale, y/scale))
            }
        }
        return newImage
    }

    // Holds data about the size and dimensions of the data this image will be made of
    private class DrawingArea(map: Collection<CoordinateWithValue<*>>){
        val maxX = map.maxOf { it.x }
        val maxY = map.maxOf { it.y }
        val minX = map.minOf { it.x }
        val minY = map.minOf { it.y }
        val width = maxX - minX
        val offsetX = minX * -1
        val height = maxY - minY
        val offsetY = minY * -1
    }

    /**
     * Generate a color from a CoordinateWithValue
     */
    fun interface ColorMaker<T>{
        fun getColor(value: CoordinateWithValue<T>): Int
    }
}