package org.codetome.zircon.internal.font.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.PhysicalFontResource
import org.junit.Ignore
import org.junit.Test

@Ignore
class Java2DPhysicalFontTest {

    val target = PhysicalFontResource.ANONYMOUS_PRO.toFont()

    @Test
    fun shouldProperlyCacheFontWhenFetchingRegionTwice() {
        val firstResult = target.fetchRegionForChar(Tile.defaultTile())
        val secondResult = target.fetchRegionForChar(Tile.defaultTile())

        assertThat(firstResult).isSameAs(secondResult)
    }

    @Test
    fun shouldNotBeTheSameWhenRegionIsLoadedForDifferentTextChars() {
        val firstResult = target.fetchRegionForChar(Tile.defaultTile())
        val secondResult = target.fetchRegionForChar(Tile.empty())

        assertThat(firstResult).isNotSameAs(secondResult)
    }

    @Test
    fun shouldBeAbleToDisplayASimpleCharacter() {
        assertThat(target.hasDataForChar('a')).isTrue()
    }

    @Test
    fun shouldNotBeAbleToDisplayControlCharacter() {
        assertThat(target.hasDataForChar(1.toChar())).isFalse()
    }

    @Test
    fun shouldNotHaveMetadataForAChar() {
        assertThat(target.fetchMetadataForChar('a')).isEmpty()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotLoadNonMonospaceFont() {
        PhysicalFontResource.loadPhysicalFont(
                size = 20f,
                path = "src/test/resources/non_mono_font/OpenSans-Regular.ttf",
                cacheFonts = true,
                withAntiAlias = true)
    }
}
