package com.volodya262
package model

import io.circe._
import io.circe.generic.semiauto._

case class CountrySource(
                          name: CountrySourceName,
                          capital: List[String],
                          region: String,
                          area: Double
                        ) {
  def toCountryDto: CountryDto =
    CountryDto(
      name = name.official,
      capital = capital.head,
      area = area
    )
}

case class CountrySourceName(official: String)

object JsonDecoders {
  implicit val countrySourceNameDecoder: Decoder[CountrySourceName] = deriveDecoder
  implicit val countrySourceDecoder: Decoder[CountrySource] = deriveDecoder
}