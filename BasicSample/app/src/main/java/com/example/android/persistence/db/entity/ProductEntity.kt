/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.persistence.db.entity

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Entity(tableName = "products")
data class ProductEntity(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var name: String,
        var description: String,
        var price: Int
) {


    @Dao
    interface IDao {
        @Query("SELECT * FROM products")
        fun loadAllProducts(): LiveData<List<ProductEntity>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAll(products: List<ProductEntity>)

        @Query("select * from products where id = :productId")
        fun loadProduct(productId: Int): LiveData<ProductEntity>

        @Query("select * from products where id = :productId")
        fun loadProductSync(productId: Int): ProductEntity
    }

}
