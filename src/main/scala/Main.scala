package com.volodya262

import model.JsonDecoders._
import model.JsonEncoders._
import model.{CountryDto, CountrySource}
import utils.AutoCloseableUtils.using

import io.circe.generic.semiauto.deriveEncoder
import io.circe.parser._
import io.circe.syntax.EncoderOps

import java.io.{File, FileWriter}
import scala.io.Source

object Main {

  def main(args: Array[String]): Unit = {

    val jsonString = using(
      closeableResource = scala.io.Source.fromFile("src/main/resources/01_countries.json"),
      (fileSource: Source) => fileSource.mkString
    )

    val json = parse(jsonString)
    val result = json.flatMap(_.as[List[CountrySource]])

    val countriesSources = result match {
      case Right(countries) => countries
      case Left(parsingError) => throw new IllegalArgumentException(s"Invalid JSON object: ${parsingError.getMessage}")
    }

    val top10BiggestCountries = countriesSources
      .filter(_.region == "Africa")
      .sortBy(_.area)(Ordering[Double].reverse)
      .take(10)
      .map(_.toCountryDto)

    val top10JsonString = top10BiggestCountries.asJson(deriveEncoder[List[CountryDto]]).spaces4

    println(top10JsonString)

    using(
      new FileWriter(new File("result.json")),
      (fileWriter: FileWriter) => fileWriter.write(top10JsonString)
    )
  }


}
