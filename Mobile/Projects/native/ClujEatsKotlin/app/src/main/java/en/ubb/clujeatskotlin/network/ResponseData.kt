package en.ubb.clujeatskotlin.network

import com.google.gson.annotations.SerializedName

class ResponseData(
    @SerializedName("success")
    var message: String = "",
    @SerializedName("name")
    var name: List<String> = emptyList(),
    @SerializedName("address")
    var address: List<String> = emptyList(),
    @SerializedName("photo")
    var photo: List<String> = emptyList(),
    @SerializedName("remoteId")
    var remoteId: Long = -1

) {
}