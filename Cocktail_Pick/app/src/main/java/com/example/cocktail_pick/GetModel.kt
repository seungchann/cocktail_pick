package com.example.cocktail_pick

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserReceive (
    var id: Int,
    var email: String,
    @SerializedName("username")
    var userName: String,
    @SerializedName("profile_url")
    var profileURL: String,
    @SerializedName("prefer_recipe_list")
    var preferRecipeList: List<RecipeReceive>,
    @SerializedName("my_tag")
    var myTag: List<String>,
    var recipes: List<RecipeReceive>
): Serializable

data class TagReceive (
    var id: Int,
    var color: String,
    var taste: String,
    var users_name: List<String>
): Serializable

data class PreferUser (
    var username: String,
    var profile_url: String,
): Serializable

data class RecipeReceive (
    var id: Int,
    var intro: String,
    var alcohol: Int,
    var writer: String,
    var tags: List<Int>,
    @SerializedName("cocktail_name")
    var cocktailName: String,
    var glass: String,
    var ice: Int,
    @SerializedName("garnish_first")
    var garnishFirst: String,
    @SerializedName("garnish_second")
    var garnishSecond: String,
    var posting: String,
    @SerializedName("base_name")
    var base: BaseReceive,
    @SerializedName("liqueur_name")
    var liqueur: LiqueurReceive,
    @SerializedName("juice_name")
    var juice: JuiceReceive,
    @SerializedName("etc_name")
    var etc: EtcReceive,
    var like_num: Int,
    var prefer_user_lists: List<List<String>>,
): Serializable

data class BaseReceive (
    var name: String,
    var onz: Float,
    var company: String,
): Serializable

data class JuiceReceive (
    var name: String,
    var onz: Float,
): Serializable

data class LiqueurReceive (
    var name: String,
    var onz: Float,
    var company: String,
): Serializable

data class EtcReceive (
    var name: String,
    var onz: Float,
): Serializable