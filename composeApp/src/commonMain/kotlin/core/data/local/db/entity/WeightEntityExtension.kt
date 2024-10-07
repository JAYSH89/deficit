package core.data.local.db.entity

import core.model.weight.Weight
import kotlinx.datetime.LocalDateTime
import migrations.WeightEntity

fun WeightEntity.toWeight() = Weight(
    id = this.id,
    weight = this.weight,
    date = LocalDateTime.parse(this.date),
)
