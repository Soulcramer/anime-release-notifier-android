package moe.notify.animenotifier.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.realm.RealmList;
import java.io.IOException;

public class RealmStringListTypeAdapter extends TypeAdapter<RealmList<RealmString>> {
  public static final TypeAdapter<RealmList<RealmString>> INSTANCE =
      new RealmStringListTypeAdapter().nullSafe();

  private RealmStringListTypeAdapter() {
  }

  @Override public void write(JsonWriter out, RealmList<RealmString> src) throws IOException {
    out.beginArray();
    for (RealmString realmString : src) {
      out.value(realmString.getValue());
    }
    out.endArray();
  }

  @Override public RealmList<RealmString> read(JsonReader in) throws IOException {
    RealmList<RealmString> realmStrings = new RealmList<>();
    in.beginArray();
    while (in.hasNext()) {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
      } else {
        RealmString realmString = new RealmString();
        realmString.setValue(in.nextString());
        realmStrings.add(realmString);
      }
    }
    in.endArray();
    return realmStrings;
  }
}
