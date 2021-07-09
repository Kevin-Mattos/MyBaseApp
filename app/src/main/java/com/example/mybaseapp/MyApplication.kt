package com.example.mybaseapp

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true)//TODO Caso use Banco
//        startKoin {TODO caso Koin
//            androidContext(applicationContext)
//            modules(mainModule)
//        }
    }

//    private val mainModule = module {
//        single<ShopDatabase> {
//            Room.databaseBuilder(
//                androidApplication(),
//                ShopDatabase::class.java, "shop.db"
//            )
//                .fallbackToDestructiveMigration()
//                .build()
//        }
//
//        single<ShopDao> { get<ShopDatabase>().shopDao() }
//
//        single<ShopDatabaseManager> {ShopDatabaseManager(get()) }
//
//        single<ShopRepository> { ShopRepository(get()) }
//    }
}