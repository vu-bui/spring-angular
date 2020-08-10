package com.example.demo.model

import javax.persistence.*
import kotlin.jvm.internal.MutablePropertyReference1
import kotlin.reflect.full.cast
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

interface BaseModel {
  val id: Long
}

fun <T : BaseModel> T.merge(source: T): T {
  if (this::class != source::class) {
    throw IllegalArgumentException("Cannot merge of object of ${source::class} to ${this::class}")
  }
  this::class.memberProperties.forEach { prop ->
    // read-only or abstract property
    if (prop !is MutablePropertyReference1 || prop.isAbstract) {
      return@forEach
    }

    // Id field
    prop.findAnnotation<Id>()?.also {
      return@forEach
    }

    // property marked as not updatable
    if (prop.findAnnotation<Column>()?.updatable == false) {
      return@forEach
    }
    if (prop.findAnnotation<JoinColumn>()?.updatable == false) {
      return@forEach
    }

    // property that is not cascade
    if (prop.findAnnotation<OneToOne>()?.cascade?.isEmpty() == true) {
      return@forEach
    }
    if (prop.findAnnotation<OneToMany>()?.cascade?.isEmpty() == true) {
      return@forEach
    }
    if (prop.findAnnotation<ManyToOne>()?.cascade?.isEmpty() == true) {
      return@forEach
    }
    if (prop.findAnnotation<ManyToMany>()?.cascade?.isEmpty() == true) {
      return@forEach
    }

    // merge value
    val curVal = prop.get(this)
    val nextVal = prop.get(source)
    if (curVal == null) {
      prop.set(this, nextVal)
      return@forEach
    }

    // deep merge
    if (curVal is BaseModel) {
      curVal.merge(nextVal as BaseModel)
      return@forEach
    }

    // collection merge
    if (curVal is Collection<*> && nextVal is Collection<*>) {
      prop.set(this, nextVal
        .map(BaseModel::class::cast)
        .map {
          curVal.stream()
            .map(BaseModel::class::cast)
            .filter { v -> v.id == it.id }
            .findFirst()
            .orElse(null)
            ?.merge(it)
            ?: it
        })
      return@forEach
    }
  }
  return this
}
