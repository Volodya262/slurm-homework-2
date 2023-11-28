package com.volodya262
package model

import io.circe._
import io.circe.generic.semiauto._

case class CountryDto(name: String, capital: String, area: Double)

object JsonEncoders {
  implicit val countryDtoEncoder: Encoder[CountryDto] = deriveEncoder
}
