package com.github.shk0da.bioritmic.api.model.user

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.shk0da.bioritmic.api.domain.GisUser
import com.github.shk0da.bioritmic.api.domain.User
import com.github.shk0da.bioritmic.api.model.BasicPresentation
import com.github.shk0da.bioritmic.api.model.search.Gender
import com.github.shk0da.bioritmic.api.service.BiorhythmService
import com.github.shk0da.bioritmic.api.utils.ImageUtils
import java.util.*

data class UserInfo(@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    val id: Long? = null,
                    val name: String? = null,
                    val email: String? = null,
                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                    val birthday: Date? = null,
                    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    val age: Int? = null,
                    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    val gender: Gender? = null,
                    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    val compare: HashMap<String, Double>? = null,
                    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    val isBioCompatible: Boolean? = null,
                    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    val isHoroCompatible: Boolean? = null,
                    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    val isFullCompatible: Boolean? = null,
                    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    val image: String? = null,
                    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    val lat: Double? = null,
                    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    val lon: Double? = null,
                    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    val distance: Double? = null) : BasicPresentation {

    companion object {

        private val biorhythmService: BiorhythmService = BiorhythmService.instance

        fun of(user: User): UserInfo {
            return UserInfo(
                    id = user.id,
                    name = user.name,
                    email = user.email,
                    birthday = Date(user.birthday!!.time),
                    gender = user.getGender(),
                    image = ImageUtils.getProfileImageUri(user.id!!)
            )
        }

        fun of(gisUser: GisUser): UserInfo {
            return UserInfo(
                    id = gisUser.id,
                    name = gisUser.name,
                    age = biorhythmService.calculateAge(Date(gisUser.birthday!!.time)),
                    gender = gisUser.getGender(),
                    lat = gisUser.lat,
                    lon = gisUser.lon,
                    distance = gisUser.distance,
                    image = ImageUtils.getProfileImageUri(gisUser.id!!)
            )
        }

        fun ofWithCompare(gisUser: GisUser, meBirthday: Date): UserInfo {
            val gisUserBirthday = Date(gisUser.birthday!!.time)
            val compare = biorhythmService.compare(gisUserBirthday, meBirthday)
            val isBioCompatible = biorhythmService.boolCompare(compare)
            val isHoroCompatible = biorhythmService.horoCompare(gisUserBirthday, meBirthday)
            val isFullCompatible = isBioCompatible && isHoroCompatible
            return UserInfo(
                    id = gisUser.id,
                    name = gisUser.name,
                    age = biorhythmService.calculateAge(Date(gisUser.birthday!!.time)),
                    gender = gisUser.getGender(),
                    lat = gisUser.lat,
                    lon = gisUser.lon,
                    distance = gisUser.distance,
                    compare = compare,
                    isBioCompatible = isBioCompatible,
                    isHoroCompatible = isHoroCompatible,
                    isFullCompatible = isFullCompatible,
                    image = ImageUtils.getProfileImageUri(gisUser.id!!)
            )
        }
    }
}