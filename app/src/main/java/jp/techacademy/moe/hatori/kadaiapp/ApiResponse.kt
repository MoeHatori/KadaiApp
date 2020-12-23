package jp.techacademy.moe.hatori.kadaiapp
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ApiResponse(
    @SerializedName("ResultSet")
    val resultSet: ResultSet
)

data class ResultSet(
    @SerializedName("Course")
    val course: List<Course>?
): Serializable

data class Course(
    @SerializedName("Price")
    val price: List<Price>,
    @SerializedName("Route")
    val route: Route
): Serializable

data class Price(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("Oneway")
    val oneway: String
): Serializable


//class MultipleType<T> {
//    var value: T? = null
//    var values: List<T>? = null
//
//    val isMultiple: Boolean
//        get() = values != null
//}

data class Route(
    @SerializedName("Line")
    val line: List<Line>,
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
): Serializable


data class Line(
    @SerializedName("ArrivalState")
    val arrivalState: ArrivalState,
    @SerializedName("DepartureState")
    val departureState: DepartureState,
    @SerializedName("Name")
    val name: String,
    @SerializedName("timeOnBoard")
    val timeOnBoard: String,
    @SerializedName("Type")
    val type: Any
): Serializable

data class Point(
    @SerializedName("Station")
    val station: Station
): Serializable

data class ArrivalState(
    @SerializedName("Datetime")
    val datetime: Datetime
): Serializable

data class DepartureState(
    @SerializedName("Datetime")
    val datetime: DatetimeX
): Serializable


data class Datetime(
    @SerializedName("text")
    val text: String
): Serializable

data class DatetimeX(
    @SerializedName("text")
    val text: String
): Serializable

data class Station(
    @SerializedName("Name")
    val name: String
): Serializable