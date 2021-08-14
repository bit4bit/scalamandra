package org.bit4bit.scalamandra.model

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map

trait Field {
  // access and update internal value
  def value: Value
  def value_=(v: Any): Unit

  def initial_value(): Value

  def definition(): Field.Definition
}

object Field {
  //https://hg.tryton.org/trytond/file/tip/trytond/model/fields/field.py#l436
  case class Definition(name: String, _type: String) {
  }

  case class Char(name: String, default: String) extends Field {
    var internal: Value = Value.Str(default)

    def value = internal
    def value_=(v: Any): Unit = {
      v match {
        case vs: String =>
          internal = Value.Str(vs)
        case _ =>
          throw new IllegalArgumentException("can't handle type")
      }
    }
    
    override def initial_value(): Value = {
      Value.Str(default)
    }

    override def definition(): Definition = {
      Definition(name = name, _type = "char")
    }
  }

  case class Int(name: String, default: Integer) extends Field {
    private var internal: Value = Value.Int(default)

    def value = internal
    def value_=(v: Any): Unit = {
      v match {
        case vs: Integer =>
          internal = Value.Int(vs)
        case _ =>
          throw new IllegalArgumentException("can't handle type")
      }
    }


    override def initial_value(): Value = {
      Value.Int(default)
    }

    override def definition(): Definition = {
      Definition(name = name, _type = "int")
    }
  }
}

// declarar campos del modelo
class Schema {
  val fields = Map[String, Field]()

  def Char(name: String, default: String = ""): Unit = {
    fields(name) = Field.Char(name = name, default = default)
  }

  def Int(name: String, default: Integer = 0): Unit = {
    fields(name) = Field.Int(name = name, default = default)
  }
}
