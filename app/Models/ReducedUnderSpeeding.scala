package Models

case class ReducedUnderSpeeding(
  underSpeed5Below: Int,
  underSpeed5TLT10: Int,
  underSpeed10TLT15 : Int,
  underSpeed15TLT20 : Int,
  underSpeed20TLT25 : Int,
  underSpeed25TLT30 : Int,
  underSpeed30TLT35 : Int,
  underSpeed35TLT40 : Int,
  underSpeed40TLT45 : Int,
  underSpeed45TLT50 : Int,
  underSpeed50TLT55 : Int,
  underSpeed55TLT60 : Int,
  underSpeed60TLT65 : Int,
  underSpeed65TLT70 : Int,
  underSpeed70TLT75 : Int,
  underSpeed75TLT80 : Int,
  underSpeed80TLT85 : Int,
  underSpeed85TLT90 : Int,
  underSpeed90TLT95 : Int,
  underSpeed95Above : Int,
  Latitude : Double,
  Longitude : Double
)
