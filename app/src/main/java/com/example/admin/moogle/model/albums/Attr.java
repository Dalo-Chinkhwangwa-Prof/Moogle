
package com.example.admin.moogle.model.albums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attr {

    @SerializedName("for")
    @Expose
    private String _for;

    public String getFor() {
        return _for;
    }

    public void setFor(String _for) {
        this._for = _for;
    }

}
