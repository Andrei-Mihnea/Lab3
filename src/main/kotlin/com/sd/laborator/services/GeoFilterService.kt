package com.sd.laborator.services

import com.sd.laborator.interfaces.GeoFilterInterface
import org.springframework.stereotype.Service

@Service
class GeoFilterService: GeoFilterInterface {
    private val blacklistedZones = listOf("Moscow", "Washington","Rome")

    override fun isAccessAllowed(zone:String): Boolean {
        return !blacklistedZones.contains(zone)
    }
}