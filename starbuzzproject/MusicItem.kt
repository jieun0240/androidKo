package com.example.starbuzzproject

import android.os.Parcel
import android.os.Parcelable

data class MusicItem(
    val title: String,
    val singer: String,
    val date: String,
    val albumCover: Int,
    val songFile: Int // 추가된 속성
) : Parcelable {

    constructor(parcel: Parcel) : this(
        title = parcel.readString() ?: "",
        singer = parcel.readString() ?: "",
        date = parcel.readString() ?: "",
        albumCover = parcel.readInt(),
        songFile = parcel.readInt() // 추가된 부분
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(singer)
        parcel.writeString(date)
        parcel.writeInt(albumCover)
        parcel.writeInt(songFile) // 추가된 부분
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<MusicItem> {
        override fun createFromParcel(parcel: Parcel): MusicItem {
            return MusicItem(parcel)
        }

        override fun newArray(size: Int): Array<MusicItem?> {
            return arrayOfNulls(size)
        }
    }
}