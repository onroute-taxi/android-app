package com.enamakel.backseattester.data.models.base;


import android.os.Parcel;
import android.os.Parcelable;


/**
 * A {@link BaseParcelableModel} is special base model, with the ability to put into a Parcelable.
 * This allows us to send the object inside of Intents.
 *
 * The Parcelable is created by serializing and de-serializing with gson. Make sure you implement
 * the static CREATOR field which takes the serialized string and create an object out of it. See
 * existing implementation to get an idea.
 */
public abstract class BaseParcelableModel extends BaseModel implements Parcelable {

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String json = gson.toJson(this);
        dest.writeString(json);
    }
}
