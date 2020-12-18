package jp.techacademy.moe.hatori.kadaiapp
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ApiResponse_Notransfer(
    @SerializedName("ResultSet")
    val resultSet: ResultSet_single
)

data class ResultSet_single(
    @SerializedName("Course")
    val course: List<Course_single>
)

data class Course_single(
    @SerializedName("Price")
    val price: List<Price_single>,
    @SerializedName("Route")
    val route: Route_single
)

data class Price_single(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("Oneway")
    val oneway: String
)

data class Route_single(
    @SerializedName("Line")
    val line: Line,
    @SerializedName("Point")
    val point: List<Point>,
    @SerializedName("timeOnBoard")
    val timeOnBoard: String,
    @SerializedName("timeOther")
    val timeOther: String,
    @SerializedName("timeWalk")
    val timeWalk: String,
    @SerializedName("transferCount")
    val transferCount: String
)


data class Line_single(
    @SerializedName("ArrivalState")
    val arrivalState: ArrivalState_single,
    @SerializedName("DepartureState")
    val departureState: DepartureState_single,
    @SerializedName("Name")
    val name: String,
    @SerializedName("timeOnBoard")
    val timeOnBoard: String,
    @SerializedName("Type")
    val type: String
)

data class Point_single(
    @SerializedName("Station")
    val station: Station_single
)

data class ArrivalState_single(
    @SerializedName("Datetime")
    val datetime: Datetime_single,
)

data class DepartureState_single(
    @SerializedName("Datetime")
    val datetime: DatetimeX,
)


data class Datetime_single(
    @SerializedName("text")
    val text: String
)

data class Station_single(
    @SerializedName("Name")
    val name: String,

    )