// ORM class for table 'songs'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Wed Apr 15 22:48:11 MSK 2026
// For connector: org.apache.sqoop.manager.PostgresqlManager
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import org.apache.sqoop.lib.JdbcWritableBridge;
import org.apache.sqoop.lib.DelimiterSet;
import org.apache.sqoop.lib.FieldFormatter;
import org.apache.sqoop.lib.RecordParser;
import org.apache.sqoop.lib.BooleanParser;
import org.apache.sqoop.lib.BlobRef;
import org.apache.sqoop.lib.ClobRef;
import org.apache.sqoop.lib.LargeObjectLoader;
import org.apache.sqoop.lib.SqoopRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class codegen_songs extends SqoopRecord  implements DBWritable, Writable {
  private final int PROTOCOL_VERSION = 3;
  public int getClassFormatVersion() { return PROTOCOL_VERSION; }
  public static interface FieldSetterCommand {    void setField(Object value);  }  protected ResultSet __cur_result_set;
  private Map<String, FieldSetterCommand> setters = new HashMap<String, FieldSetterCommand>();
  private void init0() {
    setters.put("id", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.id = (Integer)value;
      }
    });
    setters.put("af_danceability", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.af_danceability = (Double)value;
      }
    });
    setters.put("af_energy", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.af_energy = (Double)value;
      }
    });
    setters.put("key_sin", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.key_sin = (Double)value;
      }
    });
    setters.put("key_cos", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.key_cos = (Double)value;
      }
    });
    setters.put("af_loudness", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.af_loudness = (Double)value;
      }
    });
    setters.put("af_mode", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.af_mode = (Integer)value;
      }
    });
    setters.put("af_speechiness", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.af_speechiness = (Double)value;
      }
    });
    setters.put("af_acousticness", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.af_acousticness = (Double)value;
      }
    });
    setters.put("af_instrumentalness", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.af_instrumentalness = (Double)value;
      }
    });
    setters.put("af_liveness", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.af_liveness = (Double)value;
      }
    });
    setters.put("af_valence", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.af_valence = (Double)value;
      }
    });
    setters.put("af_tempo", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.af_tempo = (Double)value;
      }
    });
    setters.put("af_time_signature", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.af_time_signature = (Double)value;
      }
    });
    setters.put("duration_ms", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.duration_ms = (Double)value;
      }
    });
    setters.put("explicit", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.explicit = (Integer)value;
      }
    });
    setters.put("release_month", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.release_month = (Integer)value;
      }
    });
    setters.put("release_day_of_week", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.release_day_of_week = (Integer)value;
      }
    });
    setters.put("release_week_of_year", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.release_week_of_year = (Integer)value;
      }
    });
    setters.put("num_markets", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.num_markets = (Integer)value;
      }
    });
    setters.put("is_market_us", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.is_market_us = (Integer)value;
      }
    });
    setters.put("is_market_gb", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.is_market_gb = (Integer)value;
      }
    });
    setters.put("is_market_de", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.is_market_de = (Integer)value;
      }
    });
    setters.put("is_market_jp", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.is_market_jp = (Integer)value;
      }
    });
    setters.put("is_market_br", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.is_market_br = (Integer)value;
      }
    });
    setters.put("is_market_fr", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.is_market_fr = (Integer)value;
      }
    });
    setters.put("is_market_ca", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.is_market_ca = (Integer)value;
      }
    });
    setters.put("is_market_au", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.is_market_au = (Integer)value;
      }
    });
    setters.put("artist_prior_tracks_count", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.artist_prior_tracks_count = (Integer)value;
      }
    });
    setters.put("artist_avg_past_streams", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.artist_avg_past_streams = (Double)value;
      }
    });
    setters.put("streams_in_the_first_month", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        codegen_songs.this.streams_in_the_first_month = (Double)value;
      }
    });
  }
  public codegen_songs() {
    init0();
  }
  private Integer id;
  public Integer get_id() {
    return id;
  }
  public void set_id(Integer id) {
    this.id = id;
  }
  public codegen_songs with_id(Integer id) {
    this.id = id;
    return this;
  }
  private Double af_danceability;
  public Double get_af_danceability() {
    return af_danceability;
  }
  public void set_af_danceability(Double af_danceability) {
    this.af_danceability = af_danceability;
  }
  public codegen_songs with_af_danceability(Double af_danceability) {
    this.af_danceability = af_danceability;
    return this;
  }
  private Double af_energy;
  public Double get_af_energy() {
    return af_energy;
  }
  public void set_af_energy(Double af_energy) {
    this.af_energy = af_energy;
  }
  public codegen_songs with_af_energy(Double af_energy) {
    this.af_energy = af_energy;
    return this;
  }
  private Double key_sin;
  public Double get_key_sin() {
    return key_sin;
  }
  public void set_key_sin(Double key_sin) {
    this.key_sin = key_sin;
  }
  public codegen_songs with_key_sin(Double key_sin) {
    this.key_sin = key_sin;
    return this;
  }
  private Double key_cos;
  public Double get_key_cos() {
    return key_cos;
  }
  public void set_key_cos(Double key_cos) {
    this.key_cos = key_cos;
  }
  public codegen_songs with_key_cos(Double key_cos) {
    this.key_cos = key_cos;
    return this;
  }
  private Double af_loudness;
  public Double get_af_loudness() {
    return af_loudness;
  }
  public void set_af_loudness(Double af_loudness) {
    this.af_loudness = af_loudness;
  }
  public codegen_songs with_af_loudness(Double af_loudness) {
    this.af_loudness = af_loudness;
    return this;
  }
  private Integer af_mode;
  public Integer get_af_mode() {
    return af_mode;
  }
  public void set_af_mode(Integer af_mode) {
    this.af_mode = af_mode;
  }
  public codegen_songs with_af_mode(Integer af_mode) {
    this.af_mode = af_mode;
    return this;
  }
  private Double af_speechiness;
  public Double get_af_speechiness() {
    return af_speechiness;
  }
  public void set_af_speechiness(Double af_speechiness) {
    this.af_speechiness = af_speechiness;
  }
  public codegen_songs with_af_speechiness(Double af_speechiness) {
    this.af_speechiness = af_speechiness;
    return this;
  }
  private Double af_acousticness;
  public Double get_af_acousticness() {
    return af_acousticness;
  }
  public void set_af_acousticness(Double af_acousticness) {
    this.af_acousticness = af_acousticness;
  }
  public codegen_songs with_af_acousticness(Double af_acousticness) {
    this.af_acousticness = af_acousticness;
    return this;
  }
  private Double af_instrumentalness;
  public Double get_af_instrumentalness() {
    return af_instrumentalness;
  }
  public void set_af_instrumentalness(Double af_instrumentalness) {
    this.af_instrumentalness = af_instrumentalness;
  }
  public codegen_songs with_af_instrumentalness(Double af_instrumentalness) {
    this.af_instrumentalness = af_instrumentalness;
    return this;
  }
  private Double af_liveness;
  public Double get_af_liveness() {
    return af_liveness;
  }
  public void set_af_liveness(Double af_liveness) {
    this.af_liveness = af_liveness;
  }
  public codegen_songs with_af_liveness(Double af_liveness) {
    this.af_liveness = af_liveness;
    return this;
  }
  private Double af_valence;
  public Double get_af_valence() {
    return af_valence;
  }
  public void set_af_valence(Double af_valence) {
    this.af_valence = af_valence;
  }
  public codegen_songs with_af_valence(Double af_valence) {
    this.af_valence = af_valence;
    return this;
  }
  private Double af_tempo;
  public Double get_af_tempo() {
    return af_tempo;
  }
  public void set_af_tempo(Double af_tempo) {
    this.af_tempo = af_tempo;
  }
  public codegen_songs with_af_tempo(Double af_tempo) {
    this.af_tempo = af_tempo;
    return this;
  }
  private Double af_time_signature;
  public Double get_af_time_signature() {
    return af_time_signature;
  }
  public void set_af_time_signature(Double af_time_signature) {
    this.af_time_signature = af_time_signature;
  }
  public codegen_songs with_af_time_signature(Double af_time_signature) {
    this.af_time_signature = af_time_signature;
    return this;
  }
  private Double duration_ms;
  public Double get_duration_ms() {
    return duration_ms;
  }
  public void set_duration_ms(Double duration_ms) {
    this.duration_ms = duration_ms;
  }
  public codegen_songs with_duration_ms(Double duration_ms) {
    this.duration_ms = duration_ms;
    return this;
  }
  private Integer explicit;
  public Integer get_explicit() {
    return explicit;
  }
  public void set_explicit(Integer explicit) {
    this.explicit = explicit;
  }
  public codegen_songs with_explicit(Integer explicit) {
    this.explicit = explicit;
    return this;
  }
  private Integer release_month;
  public Integer get_release_month() {
    return release_month;
  }
  public void set_release_month(Integer release_month) {
    this.release_month = release_month;
  }
  public codegen_songs with_release_month(Integer release_month) {
    this.release_month = release_month;
    return this;
  }
  private Integer release_day_of_week;
  public Integer get_release_day_of_week() {
    return release_day_of_week;
  }
  public void set_release_day_of_week(Integer release_day_of_week) {
    this.release_day_of_week = release_day_of_week;
  }
  public codegen_songs with_release_day_of_week(Integer release_day_of_week) {
    this.release_day_of_week = release_day_of_week;
    return this;
  }
  private Integer release_week_of_year;
  public Integer get_release_week_of_year() {
    return release_week_of_year;
  }
  public void set_release_week_of_year(Integer release_week_of_year) {
    this.release_week_of_year = release_week_of_year;
  }
  public codegen_songs with_release_week_of_year(Integer release_week_of_year) {
    this.release_week_of_year = release_week_of_year;
    return this;
  }
  private Integer num_markets;
  public Integer get_num_markets() {
    return num_markets;
  }
  public void set_num_markets(Integer num_markets) {
    this.num_markets = num_markets;
  }
  public codegen_songs with_num_markets(Integer num_markets) {
    this.num_markets = num_markets;
    return this;
  }
  private Integer is_market_us;
  public Integer get_is_market_us() {
    return is_market_us;
  }
  public void set_is_market_us(Integer is_market_us) {
    this.is_market_us = is_market_us;
  }
  public codegen_songs with_is_market_us(Integer is_market_us) {
    this.is_market_us = is_market_us;
    return this;
  }
  private Integer is_market_gb;
  public Integer get_is_market_gb() {
    return is_market_gb;
  }
  public void set_is_market_gb(Integer is_market_gb) {
    this.is_market_gb = is_market_gb;
  }
  public codegen_songs with_is_market_gb(Integer is_market_gb) {
    this.is_market_gb = is_market_gb;
    return this;
  }
  private Integer is_market_de;
  public Integer get_is_market_de() {
    return is_market_de;
  }
  public void set_is_market_de(Integer is_market_de) {
    this.is_market_de = is_market_de;
  }
  public codegen_songs with_is_market_de(Integer is_market_de) {
    this.is_market_de = is_market_de;
    return this;
  }
  private Integer is_market_jp;
  public Integer get_is_market_jp() {
    return is_market_jp;
  }
  public void set_is_market_jp(Integer is_market_jp) {
    this.is_market_jp = is_market_jp;
  }
  public codegen_songs with_is_market_jp(Integer is_market_jp) {
    this.is_market_jp = is_market_jp;
    return this;
  }
  private Integer is_market_br;
  public Integer get_is_market_br() {
    return is_market_br;
  }
  public void set_is_market_br(Integer is_market_br) {
    this.is_market_br = is_market_br;
  }
  public codegen_songs with_is_market_br(Integer is_market_br) {
    this.is_market_br = is_market_br;
    return this;
  }
  private Integer is_market_fr;
  public Integer get_is_market_fr() {
    return is_market_fr;
  }
  public void set_is_market_fr(Integer is_market_fr) {
    this.is_market_fr = is_market_fr;
  }
  public codegen_songs with_is_market_fr(Integer is_market_fr) {
    this.is_market_fr = is_market_fr;
    return this;
  }
  private Integer is_market_ca;
  public Integer get_is_market_ca() {
    return is_market_ca;
  }
  public void set_is_market_ca(Integer is_market_ca) {
    this.is_market_ca = is_market_ca;
  }
  public codegen_songs with_is_market_ca(Integer is_market_ca) {
    this.is_market_ca = is_market_ca;
    return this;
  }
  private Integer is_market_au;
  public Integer get_is_market_au() {
    return is_market_au;
  }
  public void set_is_market_au(Integer is_market_au) {
    this.is_market_au = is_market_au;
  }
  public codegen_songs with_is_market_au(Integer is_market_au) {
    this.is_market_au = is_market_au;
    return this;
  }
  private Integer artist_prior_tracks_count;
  public Integer get_artist_prior_tracks_count() {
    return artist_prior_tracks_count;
  }
  public void set_artist_prior_tracks_count(Integer artist_prior_tracks_count) {
    this.artist_prior_tracks_count = artist_prior_tracks_count;
  }
  public codegen_songs with_artist_prior_tracks_count(Integer artist_prior_tracks_count) {
    this.artist_prior_tracks_count = artist_prior_tracks_count;
    return this;
  }
  private Double artist_avg_past_streams;
  public Double get_artist_avg_past_streams() {
    return artist_avg_past_streams;
  }
  public void set_artist_avg_past_streams(Double artist_avg_past_streams) {
    this.artist_avg_past_streams = artist_avg_past_streams;
  }
  public codegen_songs with_artist_avg_past_streams(Double artist_avg_past_streams) {
    this.artist_avg_past_streams = artist_avg_past_streams;
    return this;
  }
  private Double streams_in_the_first_month;
  public Double get_streams_in_the_first_month() {
    return streams_in_the_first_month;
  }
  public void set_streams_in_the_first_month(Double streams_in_the_first_month) {
    this.streams_in_the_first_month = streams_in_the_first_month;
  }
  public codegen_songs with_streams_in_the_first_month(Double streams_in_the_first_month) {
    this.streams_in_the_first_month = streams_in_the_first_month;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof codegen_songs)) {
      return false;
    }
    codegen_songs that = (codegen_songs) o;
    boolean equal = true;
    equal = equal && (this.id == null ? that.id == null : this.id.equals(that.id));
    equal = equal && (this.af_danceability == null ? that.af_danceability == null : this.af_danceability.equals(that.af_danceability));
    equal = equal && (this.af_energy == null ? that.af_energy == null : this.af_energy.equals(that.af_energy));
    equal = equal && (this.key_sin == null ? that.key_sin == null : this.key_sin.equals(that.key_sin));
    equal = equal && (this.key_cos == null ? that.key_cos == null : this.key_cos.equals(that.key_cos));
    equal = equal && (this.af_loudness == null ? that.af_loudness == null : this.af_loudness.equals(that.af_loudness));
    equal = equal && (this.af_mode == null ? that.af_mode == null : this.af_mode.equals(that.af_mode));
    equal = equal && (this.af_speechiness == null ? that.af_speechiness == null : this.af_speechiness.equals(that.af_speechiness));
    equal = equal && (this.af_acousticness == null ? that.af_acousticness == null : this.af_acousticness.equals(that.af_acousticness));
    equal = equal && (this.af_instrumentalness == null ? that.af_instrumentalness == null : this.af_instrumentalness.equals(that.af_instrumentalness));
    equal = equal && (this.af_liveness == null ? that.af_liveness == null : this.af_liveness.equals(that.af_liveness));
    equal = equal && (this.af_valence == null ? that.af_valence == null : this.af_valence.equals(that.af_valence));
    equal = equal && (this.af_tempo == null ? that.af_tempo == null : this.af_tempo.equals(that.af_tempo));
    equal = equal && (this.af_time_signature == null ? that.af_time_signature == null : this.af_time_signature.equals(that.af_time_signature));
    equal = equal && (this.duration_ms == null ? that.duration_ms == null : this.duration_ms.equals(that.duration_ms));
    equal = equal && (this.explicit == null ? that.explicit == null : this.explicit.equals(that.explicit));
    equal = equal && (this.release_month == null ? that.release_month == null : this.release_month.equals(that.release_month));
    equal = equal && (this.release_day_of_week == null ? that.release_day_of_week == null : this.release_day_of_week.equals(that.release_day_of_week));
    equal = equal && (this.release_week_of_year == null ? that.release_week_of_year == null : this.release_week_of_year.equals(that.release_week_of_year));
    equal = equal && (this.num_markets == null ? that.num_markets == null : this.num_markets.equals(that.num_markets));
    equal = equal && (this.is_market_us == null ? that.is_market_us == null : this.is_market_us.equals(that.is_market_us));
    equal = equal && (this.is_market_gb == null ? that.is_market_gb == null : this.is_market_gb.equals(that.is_market_gb));
    equal = equal && (this.is_market_de == null ? that.is_market_de == null : this.is_market_de.equals(that.is_market_de));
    equal = equal && (this.is_market_jp == null ? that.is_market_jp == null : this.is_market_jp.equals(that.is_market_jp));
    equal = equal && (this.is_market_br == null ? that.is_market_br == null : this.is_market_br.equals(that.is_market_br));
    equal = equal && (this.is_market_fr == null ? that.is_market_fr == null : this.is_market_fr.equals(that.is_market_fr));
    equal = equal && (this.is_market_ca == null ? that.is_market_ca == null : this.is_market_ca.equals(that.is_market_ca));
    equal = equal && (this.is_market_au == null ? that.is_market_au == null : this.is_market_au.equals(that.is_market_au));
    equal = equal && (this.artist_prior_tracks_count == null ? that.artist_prior_tracks_count == null : this.artist_prior_tracks_count.equals(that.artist_prior_tracks_count));
    equal = equal && (this.artist_avg_past_streams == null ? that.artist_avg_past_streams == null : this.artist_avg_past_streams.equals(that.artist_avg_past_streams));
    equal = equal && (this.streams_in_the_first_month == null ? that.streams_in_the_first_month == null : this.streams_in_the_first_month.equals(that.streams_in_the_first_month));
    return equal;
  }
  public boolean equals0(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof codegen_songs)) {
      return false;
    }
    codegen_songs that = (codegen_songs) o;
    boolean equal = true;
    equal = equal && (this.id == null ? that.id == null : this.id.equals(that.id));
    equal = equal && (this.af_danceability == null ? that.af_danceability == null : this.af_danceability.equals(that.af_danceability));
    equal = equal && (this.af_energy == null ? that.af_energy == null : this.af_energy.equals(that.af_energy));
    equal = equal && (this.key_sin == null ? that.key_sin == null : this.key_sin.equals(that.key_sin));
    equal = equal && (this.key_cos == null ? that.key_cos == null : this.key_cos.equals(that.key_cos));
    equal = equal && (this.af_loudness == null ? that.af_loudness == null : this.af_loudness.equals(that.af_loudness));
    equal = equal && (this.af_mode == null ? that.af_mode == null : this.af_mode.equals(that.af_mode));
    equal = equal && (this.af_speechiness == null ? that.af_speechiness == null : this.af_speechiness.equals(that.af_speechiness));
    equal = equal && (this.af_acousticness == null ? that.af_acousticness == null : this.af_acousticness.equals(that.af_acousticness));
    equal = equal && (this.af_instrumentalness == null ? that.af_instrumentalness == null : this.af_instrumentalness.equals(that.af_instrumentalness));
    equal = equal && (this.af_liveness == null ? that.af_liveness == null : this.af_liveness.equals(that.af_liveness));
    equal = equal && (this.af_valence == null ? that.af_valence == null : this.af_valence.equals(that.af_valence));
    equal = equal && (this.af_tempo == null ? that.af_tempo == null : this.af_tempo.equals(that.af_tempo));
    equal = equal && (this.af_time_signature == null ? that.af_time_signature == null : this.af_time_signature.equals(that.af_time_signature));
    equal = equal && (this.duration_ms == null ? that.duration_ms == null : this.duration_ms.equals(that.duration_ms));
    equal = equal && (this.explicit == null ? that.explicit == null : this.explicit.equals(that.explicit));
    equal = equal && (this.release_month == null ? that.release_month == null : this.release_month.equals(that.release_month));
    equal = equal && (this.release_day_of_week == null ? that.release_day_of_week == null : this.release_day_of_week.equals(that.release_day_of_week));
    equal = equal && (this.release_week_of_year == null ? that.release_week_of_year == null : this.release_week_of_year.equals(that.release_week_of_year));
    equal = equal && (this.num_markets == null ? that.num_markets == null : this.num_markets.equals(that.num_markets));
    equal = equal && (this.is_market_us == null ? that.is_market_us == null : this.is_market_us.equals(that.is_market_us));
    equal = equal && (this.is_market_gb == null ? that.is_market_gb == null : this.is_market_gb.equals(that.is_market_gb));
    equal = equal && (this.is_market_de == null ? that.is_market_de == null : this.is_market_de.equals(that.is_market_de));
    equal = equal && (this.is_market_jp == null ? that.is_market_jp == null : this.is_market_jp.equals(that.is_market_jp));
    equal = equal && (this.is_market_br == null ? that.is_market_br == null : this.is_market_br.equals(that.is_market_br));
    equal = equal && (this.is_market_fr == null ? that.is_market_fr == null : this.is_market_fr.equals(that.is_market_fr));
    equal = equal && (this.is_market_ca == null ? that.is_market_ca == null : this.is_market_ca.equals(that.is_market_ca));
    equal = equal && (this.is_market_au == null ? that.is_market_au == null : this.is_market_au.equals(that.is_market_au));
    equal = equal && (this.artist_prior_tracks_count == null ? that.artist_prior_tracks_count == null : this.artist_prior_tracks_count.equals(that.artist_prior_tracks_count));
    equal = equal && (this.artist_avg_past_streams == null ? that.artist_avg_past_streams == null : this.artist_avg_past_streams.equals(that.artist_avg_past_streams));
    equal = equal && (this.streams_in_the_first_month == null ? that.streams_in_the_first_month == null : this.streams_in_the_first_month.equals(that.streams_in_the_first_month));
    return equal;
  }
  public void readFields(ResultSet __dbResults) throws SQLException {
    this.__cur_result_set = __dbResults;
    this.id = JdbcWritableBridge.readInteger(1, __dbResults);
    this.af_danceability = JdbcWritableBridge.readDouble(2, __dbResults);
    this.af_energy = JdbcWritableBridge.readDouble(3, __dbResults);
    this.key_sin = JdbcWritableBridge.readDouble(4, __dbResults);
    this.key_cos = JdbcWritableBridge.readDouble(5, __dbResults);
    this.af_loudness = JdbcWritableBridge.readDouble(6, __dbResults);
    this.af_mode = JdbcWritableBridge.readInteger(7, __dbResults);
    this.af_speechiness = JdbcWritableBridge.readDouble(8, __dbResults);
    this.af_acousticness = JdbcWritableBridge.readDouble(9, __dbResults);
    this.af_instrumentalness = JdbcWritableBridge.readDouble(10, __dbResults);
    this.af_liveness = JdbcWritableBridge.readDouble(11, __dbResults);
    this.af_valence = JdbcWritableBridge.readDouble(12, __dbResults);
    this.af_tempo = JdbcWritableBridge.readDouble(13, __dbResults);
    this.af_time_signature = JdbcWritableBridge.readDouble(14, __dbResults);
    this.duration_ms = JdbcWritableBridge.readDouble(15, __dbResults);
    this.explicit = JdbcWritableBridge.readInteger(16, __dbResults);
    this.release_month = JdbcWritableBridge.readInteger(17, __dbResults);
    this.release_day_of_week = JdbcWritableBridge.readInteger(18, __dbResults);
    this.release_week_of_year = JdbcWritableBridge.readInteger(19, __dbResults);
    this.num_markets = JdbcWritableBridge.readInteger(20, __dbResults);
    this.is_market_us = JdbcWritableBridge.readInteger(21, __dbResults);
    this.is_market_gb = JdbcWritableBridge.readInteger(22, __dbResults);
    this.is_market_de = JdbcWritableBridge.readInteger(23, __dbResults);
    this.is_market_jp = JdbcWritableBridge.readInteger(24, __dbResults);
    this.is_market_br = JdbcWritableBridge.readInteger(25, __dbResults);
    this.is_market_fr = JdbcWritableBridge.readInteger(26, __dbResults);
    this.is_market_ca = JdbcWritableBridge.readInteger(27, __dbResults);
    this.is_market_au = JdbcWritableBridge.readInteger(28, __dbResults);
    this.artist_prior_tracks_count = JdbcWritableBridge.readInteger(29, __dbResults);
    this.artist_avg_past_streams = JdbcWritableBridge.readDouble(30, __dbResults);
    this.streams_in_the_first_month = JdbcWritableBridge.readDouble(31, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.id = JdbcWritableBridge.readInteger(1, __dbResults);
    this.af_danceability = JdbcWritableBridge.readDouble(2, __dbResults);
    this.af_energy = JdbcWritableBridge.readDouble(3, __dbResults);
    this.key_sin = JdbcWritableBridge.readDouble(4, __dbResults);
    this.key_cos = JdbcWritableBridge.readDouble(5, __dbResults);
    this.af_loudness = JdbcWritableBridge.readDouble(6, __dbResults);
    this.af_mode = JdbcWritableBridge.readInteger(7, __dbResults);
    this.af_speechiness = JdbcWritableBridge.readDouble(8, __dbResults);
    this.af_acousticness = JdbcWritableBridge.readDouble(9, __dbResults);
    this.af_instrumentalness = JdbcWritableBridge.readDouble(10, __dbResults);
    this.af_liveness = JdbcWritableBridge.readDouble(11, __dbResults);
    this.af_valence = JdbcWritableBridge.readDouble(12, __dbResults);
    this.af_tempo = JdbcWritableBridge.readDouble(13, __dbResults);
    this.af_time_signature = JdbcWritableBridge.readDouble(14, __dbResults);
    this.duration_ms = JdbcWritableBridge.readDouble(15, __dbResults);
    this.explicit = JdbcWritableBridge.readInteger(16, __dbResults);
    this.release_month = JdbcWritableBridge.readInteger(17, __dbResults);
    this.release_day_of_week = JdbcWritableBridge.readInteger(18, __dbResults);
    this.release_week_of_year = JdbcWritableBridge.readInteger(19, __dbResults);
    this.num_markets = JdbcWritableBridge.readInteger(20, __dbResults);
    this.is_market_us = JdbcWritableBridge.readInteger(21, __dbResults);
    this.is_market_gb = JdbcWritableBridge.readInteger(22, __dbResults);
    this.is_market_de = JdbcWritableBridge.readInteger(23, __dbResults);
    this.is_market_jp = JdbcWritableBridge.readInteger(24, __dbResults);
    this.is_market_br = JdbcWritableBridge.readInteger(25, __dbResults);
    this.is_market_fr = JdbcWritableBridge.readInteger(26, __dbResults);
    this.is_market_ca = JdbcWritableBridge.readInteger(27, __dbResults);
    this.is_market_au = JdbcWritableBridge.readInteger(28, __dbResults);
    this.artist_prior_tracks_count = JdbcWritableBridge.readInteger(29, __dbResults);
    this.artist_avg_past_streams = JdbcWritableBridge.readDouble(30, __dbResults);
    this.streams_in_the_first_month = JdbcWritableBridge.readDouble(31, __dbResults);
  }
  public void loadLargeObjects(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void loadLargeObjects0(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void write(PreparedStatement __dbStmt) throws SQLException {
    write(__dbStmt, 0);
  }

  public int write(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeInteger(id, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeDouble(af_danceability, 2 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_energy, 3 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(key_sin, 4 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(key_cos, 5 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_loudness, 6 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeInteger(af_mode, 7 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeDouble(af_speechiness, 8 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_acousticness, 9 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_instrumentalness, 10 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_liveness, 11 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_valence, 12 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_tempo, 13 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_time_signature, 14 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(duration_ms, 15 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeInteger(explicit, 16 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(release_month, 17 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(release_day_of_week, 18 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(release_week_of_year, 19 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(num_markets, 20 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_us, 21 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_gb, 22 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_de, 23 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_jp, 24 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_br, 25 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_fr, 26 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_ca, 27 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_au, 28 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(artist_prior_tracks_count, 29 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeDouble(artist_avg_past_streams, 30 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(streams_in_the_first_month, 31 + __off, 8, __dbStmt);
    return 31;
  }
  public void write0(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeInteger(id, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeDouble(af_danceability, 2 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_energy, 3 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(key_sin, 4 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(key_cos, 5 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_loudness, 6 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeInteger(af_mode, 7 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeDouble(af_speechiness, 8 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_acousticness, 9 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_instrumentalness, 10 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_liveness, 11 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_valence, 12 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_tempo, 13 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(af_time_signature, 14 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(duration_ms, 15 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeInteger(explicit, 16 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(release_month, 17 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(release_day_of_week, 18 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(release_week_of_year, 19 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(num_markets, 20 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_us, 21 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_gb, 22 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_de, 23 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_jp, 24 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_br, 25 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_fr, 26 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_ca, 27 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(is_market_au, 28 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(artist_prior_tracks_count, 29 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeDouble(artist_avg_past_streams, 30 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeDouble(streams_in_the_first_month, 31 + __off, 8, __dbStmt);
  }
  public void readFields(DataInput __dataIn) throws IOException {
this.readFields0(__dataIn);  }
  public void readFields0(DataInput __dataIn) throws IOException {
    if (__dataIn.readBoolean()) { 
        this.id = null;
    } else {
    this.id = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.af_danceability = null;
    } else {
    this.af_danceability = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.af_energy = null;
    } else {
    this.af_energy = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.key_sin = null;
    } else {
    this.key_sin = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.key_cos = null;
    } else {
    this.key_cos = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.af_loudness = null;
    } else {
    this.af_loudness = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.af_mode = null;
    } else {
    this.af_mode = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.af_speechiness = null;
    } else {
    this.af_speechiness = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.af_acousticness = null;
    } else {
    this.af_acousticness = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.af_instrumentalness = null;
    } else {
    this.af_instrumentalness = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.af_liveness = null;
    } else {
    this.af_liveness = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.af_valence = null;
    } else {
    this.af_valence = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.af_tempo = null;
    } else {
    this.af_tempo = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.af_time_signature = null;
    } else {
    this.af_time_signature = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.duration_ms = null;
    } else {
    this.duration_ms = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.explicit = null;
    } else {
    this.explicit = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.release_month = null;
    } else {
    this.release_month = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.release_day_of_week = null;
    } else {
    this.release_day_of_week = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.release_week_of_year = null;
    } else {
    this.release_week_of_year = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.num_markets = null;
    } else {
    this.num_markets = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.is_market_us = null;
    } else {
    this.is_market_us = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.is_market_gb = null;
    } else {
    this.is_market_gb = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.is_market_de = null;
    } else {
    this.is_market_de = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.is_market_jp = null;
    } else {
    this.is_market_jp = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.is_market_br = null;
    } else {
    this.is_market_br = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.is_market_fr = null;
    } else {
    this.is_market_fr = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.is_market_ca = null;
    } else {
    this.is_market_ca = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.is_market_au = null;
    } else {
    this.is_market_au = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.artist_prior_tracks_count = null;
    } else {
    this.artist_prior_tracks_count = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.artist_avg_past_streams = null;
    } else {
    this.artist_avg_past_streams = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.streams_in_the_first_month = null;
    } else {
    this.streams_in_the_first_month = Double.valueOf(__dataIn.readDouble());
    }
  }
  public void write(DataOutput __dataOut) throws IOException {
    if (null == this.id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.id);
    }
    if (null == this.af_danceability) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_danceability);
    }
    if (null == this.af_energy) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_energy);
    }
    if (null == this.key_sin) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.key_sin);
    }
    if (null == this.key_cos) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.key_cos);
    }
    if (null == this.af_loudness) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_loudness);
    }
    if (null == this.af_mode) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.af_mode);
    }
    if (null == this.af_speechiness) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_speechiness);
    }
    if (null == this.af_acousticness) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_acousticness);
    }
    if (null == this.af_instrumentalness) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_instrumentalness);
    }
    if (null == this.af_liveness) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_liveness);
    }
    if (null == this.af_valence) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_valence);
    }
    if (null == this.af_tempo) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_tempo);
    }
    if (null == this.af_time_signature) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_time_signature);
    }
    if (null == this.duration_ms) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.duration_ms);
    }
    if (null == this.explicit) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.explicit);
    }
    if (null == this.release_month) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.release_month);
    }
    if (null == this.release_day_of_week) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.release_day_of_week);
    }
    if (null == this.release_week_of_year) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.release_week_of_year);
    }
    if (null == this.num_markets) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.num_markets);
    }
    if (null == this.is_market_us) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_us);
    }
    if (null == this.is_market_gb) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_gb);
    }
    if (null == this.is_market_de) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_de);
    }
    if (null == this.is_market_jp) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_jp);
    }
    if (null == this.is_market_br) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_br);
    }
    if (null == this.is_market_fr) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_fr);
    }
    if (null == this.is_market_ca) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_ca);
    }
    if (null == this.is_market_au) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_au);
    }
    if (null == this.artist_prior_tracks_count) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.artist_prior_tracks_count);
    }
    if (null == this.artist_avg_past_streams) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.artist_avg_past_streams);
    }
    if (null == this.streams_in_the_first_month) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.streams_in_the_first_month);
    }
  }
  public void write0(DataOutput __dataOut) throws IOException {
    if (null == this.id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.id);
    }
    if (null == this.af_danceability) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_danceability);
    }
    if (null == this.af_energy) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_energy);
    }
    if (null == this.key_sin) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.key_sin);
    }
    if (null == this.key_cos) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.key_cos);
    }
    if (null == this.af_loudness) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_loudness);
    }
    if (null == this.af_mode) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.af_mode);
    }
    if (null == this.af_speechiness) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_speechiness);
    }
    if (null == this.af_acousticness) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_acousticness);
    }
    if (null == this.af_instrumentalness) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_instrumentalness);
    }
    if (null == this.af_liveness) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_liveness);
    }
    if (null == this.af_valence) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_valence);
    }
    if (null == this.af_tempo) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_tempo);
    }
    if (null == this.af_time_signature) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.af_time_signature);
    }
    if (null == this.duration_ms) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.duration_ms);
    }
    if (null == this.explicit) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.explicit);
    }
    if (null == this.release_month) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.release_month);
    }
    if (null == this.release_day_of_week) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.release_day_of_week);
    }
    if (null == this.release_week_of_year) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.release_week_of_year);
    }
    if (null == this.num_markets) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.num_markets);
    }
    if (null == this.is_market_us) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_us);
    }
    if (null == this.is_market_gb) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_gb);
    }
    if (null == this.is_market_de) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_de);
    }
    if (null == this.is_market_jp) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_jp);
    }
    if (null == this.is_market_br) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_br);
    }
    if (null == this.is_market_fr) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_fr);
    }
    if (null == this.is_market_ca) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_ca);
    }
    if (null == this.is_market_au) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.is_market_au);
    }
    if (null == this.artist_prior_tracks_count) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.artist_prior_tracks_count);
    }
    if (null == this.artist_avg_past_streams) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.artist_avg_past_streams);
    }
    if (null == this.streams_in_the_first_month) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.streams_in_the_first_month);
    }
  }
  private static final DelimiterSet __outputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  public String toString() {
    return toString(__outputDelimiters, true);
  }
  public String toString(DelimiterSet delimiters) {
    return toString(delimiters, true);
  }
  public String toString(boolean useRecordDelim) {
    return toString(__outputDelimiters, useRecordDelim);
  }
  public String toString(DelimiterSet delimiters, boolean useRecordDelim) {
    StringBuilder __sb = new StringBuilder();
    char fieldDelim = delimiters.getFieldsTerminatedBy();
    __sb.append(FieldFormatter.escapeAndEnclose(id==null?"null":"" + id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_danceability==null?"null":"" + af_danceability, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_energy==null?"null":"" + af_energy, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(key_sin==null?"null":"" + key_sin, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(key_cos==null?"null":"" + key_cos, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_loudness==null?"null":"" + af_loudness, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_mode==null?"null":"" + af_mode, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_speechiness==null?"null":"" + af_speechiness, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_acousticness==null?"null":"" + af_acousticness, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_instrumentalness==null?"null":"" + af_instrumentalness, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_liveness==null?"null":"" + af_liveness, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_valence==null?"null":"" + af_valence, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_tempo==null?"null":"" + af_tempo, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_time_signature==null?"null":"" + af_time_signature, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(duration_ms==null?"null":"" + duration_ms, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(explicit==null?"null":"" + explicit, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(release_month==null?"null":"" + release_month, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(release_day_of_week==null?"null":"" + release_day_of_week, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(release_week_of_year==null?"null":"" + release_week_of_year, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(num_markets==null?"null":"" + num_markets, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_us==null?"null":"" + is_market_us, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_gb==null?"null":"" + is_market_gb, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_de==null?"null":"" + is_market_de, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_jp==null?"null":"" + is_market_jp, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_br==null?"null":"" + is_market_br, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_fr==null?"null":"" + is_market_fr, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_ca==null?"null":"" + is_market_ca, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_au==null?"null":"" + is_market_au, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(artist_prior_tracks_count==null?"null":"" + artist_prior_tracks_count, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(artist_avg_past_streams==null?"null":"" + artist_avg_past_streams, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(streams_in_the_first_month==null?"null":"" + streams_in_the_first_month, delimiters));
    if (useRecordDelim) {
      __sb.append(delimiters.getLinesTerminatedBy());
    }
    return __sb.toString();
  }
  public void toString0(DelimiterSet delimiters, StringBuilder __sb, char fieldDelim) {
    __sb.append(FieldFormatter.escapeAndEnclose(id==null?"null":"" + id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_danceability==null?"null":"" + af_danceability, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_energy==null?"null":"" + af_energy, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(key_sin==null?"null":"" + key_sin, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(key_cos==null?"null":"" + key_cos, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_loudness==null?"null":"" + af_loudness, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_mode==null?"null":"" + af_mode, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_speechiness==null?"null":"" + af_speechiness, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_acousticness==null?"null":"" + af_acousticness, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_instrumentalness==null?"null":"" + af_instrumentalness, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_liveness==null?"null":"" + af_liveness, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_valence==null?"null":"" + af_valence, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_tempo==null?"null":"" + af_tempo, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(af_time_signature==null?"null":"" + af_time_signature, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(duration_ms==null?"null":"" + duration_ms, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(explicit==null?"null":"" + explicit, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(release_month==null?"null":"" + release_month, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(release_day_of_week==null?"null":"" + release_day_of_week, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(release_week_of_year==null?"null":"" + release_week_of_year, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(num_markets==null?"null":"" + num_markets, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_us==null?"null":"" + is_market_us, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_gb==null?"null":"" + is_market_gb, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_de==null?"null":"" + is_market_de, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_jp==null?"null":"" + is_market_jp, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_br==null?"null":"" + is_market_br, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_fr==null?"null":"" + is_market_fr, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_ca==null?"null":"" + is_market_ca, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_market_au==null?"null":"" + is_market_au, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(artist_prior_tracks_count==null?"null":"" + artist_prior_tracks_count, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(artist_avg_past_streams==null?"null":"" + artist_avg_past_streams, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(streams_in_the_first_month==null?"null":"" + streams_in_the_first_month, delimiters));
  }
  private static final DelimiterSet __inputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  private RecordParser __parser;
  public void parse(Text __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharSequence __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(byte [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(char [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(ByteBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  private void __loadFromFields(List<String> fields) {
    Iterator<String> __it = fields.listIterator();
    String __cur_str = null;
    try {
    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.id = null; } else {
      this.id = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_danceability = null; } else {
      this.af_danceability = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_energy = null; } else {
      this.af_energy = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.key_sin = null; } else {
      this.key_sin = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.key_cos = null; } else {
      this.key_cos = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_loudness = null; } else {
      this.af_loudness = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_mode = null; } else {
      this.af_mode = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_speechiness = null; } else {
      this.af_speechiness = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_acousticness = null; } else {
      this.af_acousticness = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_instrumentalness = null; } else {
      this.af_instrumentalness = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_liveness = null; } else {
      this.af_liveness = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_valence = null; } else {
      this.af_valence = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_tempo = null; } else {
      this.af_tempo = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_time_signature = null; } else {
      this.af_time_signature = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.duration_ms = null; } else {
      this.duration_ms = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.explicit = null; } else {
      this.explicit = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.release_month = null; } else {
      this.release_month = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.release_day_of_week = null; } else {
      this.release_day_of_week = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.release_week_of_year = null; } else {
      this.release_week_of_year = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.num_markets = null; } else {
      this.num_markets = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_us = null; } else {
      this.is_market_us = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_gb = null; } else {
      this.is_market_gb = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_de = null; } else {
      this.is_market_de = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_jp = null; } else {
      this.is_market_jp = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_br = null; } else {
      this.is_market_br = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_fr = null; } else {
      this.is_market_fr = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_ca = null; } else {
      this.is_market_ca = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_au = null; } else {
      this.is_market_au = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.artist_prior_tracks_count = null; } else {
      this.artist_prior_tracks_count = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.artist_avg_past_streams = null; } else {
      this.artist_avg_past_streams = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.streams_in_the_first_month = null; } else {
      this.streams_in_the_first_month = Double.valueOf(__cur_str);
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  private void __loadFromFields0(Iterator<String> __it) {
    String __cur_str = null;
    try {
    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.id = null; } else {
      this.id = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_danceability = null; } else {
      this.af_danceability = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_energy = null; } else {
      this.af_energy = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.key_sin = null; } else {
      this.key_sin = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.key_cos = null; } else {
      this.key_cos = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_loudness = null; } else {
      this.af_loudness = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_mode = null; } else {
      this.af_mode = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_speechiness = null; } else {
      this.af_speechiness = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_acousticness = null; } else {
      this.af_acousticness = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_instrumentalness = null; } else {
      this.af_instrumentalness = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_liveness = null; } else {
      this.af_liveness = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_valence = null; } else {
      this.af_valence = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_tempo = null; } else {
      this.af_tempo = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.af_time_signature = null; } else {
      this.af_time_signature = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.duration_ms = null; } else {
      this.duration_ms = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.explicit = null; } else {
      this.explicit = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.release_month = null; } else {
      this.release_month = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.release_day_of_week = null; } else {
      this.release_day_of_week = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.release_week_of_year = null; } else {
      this.release_week_of_year = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.num_markets = null; } else {
      this.num_markets = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_us = null; } else {
      this.is_market_us = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_gb = null; } else {
      this.is_market_gb = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_de = null; } else {
      this.is_market_de = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_jp = null; } else {
      this.is_market_jp = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_br = null; } else {
      this.is_market_br = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_fr = null; } else {
      this.is_market_fr = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_ca = null; } else {
      this.is_market_ca = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.is_market_au = null; } else {
      this.is_market_au = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.artist_prior_tracks_count = null; } else {
      this.artist_prior_tracks_count = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.artist_avg_past_streams = null; } else {
      this.artist_avg_past_streams = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.streams_in_the_first_month = null; } else {
      this.streams_in_the_first_month = Double.valueOf(__cur_str);
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  public Object clone() throws CloneNotSupportedException {
    codegen_songs o = (codegen_songs) super.clone();
    return o;
  }

  public void clone0(codegen_songs o) throws CloneNotSupportedException {
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new HashMap<String, Object>();
    __sqoop$field_map.put("id", this.id);
    __sqoop$field_map.put("af_danceability", this.af_danceability);
    __sqoop$field_map.put("af_energy", this.af_energy);
    __sqoop$field_map.put("key_sin", this.key_sin);
    __sqoop$field_map.put("key_cos", this.key_cos);
    __sqoop$field_map.put("af_loudness", this.af_loudness);
    __sqoop$field_map.put("af_mode", this.af_mode);
    __sqoop$field_map.put("af_speechiness", this.af_speechiness);
    __sqoop$field_map.put("af_acousticness", this.af_acousticness);
    __sqoop$field_map.put("af_instrumentalness", this.af_instrumentalness);
    __sqoop$field_map.put("af_liveness", this.af_liveness);
    __sqoop$field_map.put("af_valence", this.af_valence);
    __sqoop$field_map.put("af_tempo", this.af_tempo);
    __sqoop$field_map.put("af_time_signature", this.af_time_signature);
    __sqoop$field_map.put("duration_ms", this.duration_ms);
    __sqoop$field_map.put("explicit", this.explicit);
    __sqoop$field_map.put("release_month", this.release_month);
    __sqoop$field_map.put("release_day_of_week", this.release_day_of_week);
    __sqoop$field_map.put("release_week_of_year", this.release_week_of_year);
    __sqoop$field_map.put("num_markets", this.num_markets);
    __sqoop$field_map.put("is_market_us", this.is_market_us);
    __sqoop$field_map.put("is_market_gb", this.is_market_gb);
    __sqoop$field_map.put("is_market_de", this.is_market_de);
    __sqoop$field_map.put("is_market_jp", this.is_market_jp);
    __sqoop$field_map.put("is_market_br", this.is_market_br);
    __sqoop$field_map.put("is_market_fr", this.is_market_fr);
    __sqoop$field_map.put("is_market_ca", this.is_market_ca);
    __sqoop$field_map.put("is_market_au", this.is_market_au);
    __sqoop$field_map.put("artist_prior_tracks_count", this.artist_prior_tracks_count);
    __sqoop$field_map.put("artist_avg_past_streams", this.artist_avg_past_streams);
    __sqoop$field_map.put("streams_in_the_first_month", this.streams_in_the_first_month);
    return __sqoop$field_map;
  }

  public void getFieldMap0(Map<String, Object> __sqoop$field_map) {
    __sqoop$field_map.put("id", this.id);
    __sqoop$field_map.put("af_danceability", this.af_danceability);
    __sqoop$field_map.put("af_energy", this.af_energy);
    __sqoop$field_map.put("key_sin", this.key_sin);
    __sqoop$field_map.put("key_cos", this.key_cos);
    __sqoop$field_map.put("af_loudness", this.af_loudness);
    __sqoop$field_map.put("af_mode", this.af_mode);
    __sqoop$field_map.put("af_speechiness", this.af_speechiness);
    __sqoop$field_map.put("af_acousticness", this.af_acousticness);
    __sqoop$field_map.put("af_instrumentalness", this.af_instrumentalness);
    __sqoop$field_map.put("af_liveness", this.af_liveness);
    __sqoop$field_map.put("af_valence", this.af_valence);
    __sqoop$field_map.put("af_tempo", this.af_tempo);
    __sqoop$field_map.put("af_time_signature", this.af_time_signature);
    __sqoop$field_map.put("duration_ms", this.duration_ms);
    __sqoop$field_map.put("explicit", this.explicit);
    __sqoop$field_map.put("release_month", this.release_month);
    __sqoop$field_map.put("release_day_of_week", this.release_day_of_week);
    __sqoop$field_map.put("release_week_of_year", this.release_week_of_year);
    __sqoop$field_map.put("num_markets", this.num_markets);
    __sqoop$field_map.put("is_market_us", this.is_market_us);
    __sqoop$field_map.put("is_market_gb", this.is_market_gb);
    __sqoop$field_map.put("is_market_de", this.is_market_de);
    __sqoop$field_map.put("is_market_jp", this.is_market_jp);
    __sqoop$field_map.put("is_market_br", this.is_market_br);
    __sqoop$field_map.put("is_market_fr", this.is_market_fr);
    __sqoop$field_map.put("is_market_ca", this.is_market_ca);
    __sqoop$field_map.put("is_market_au", this.is_market_au);
    __sqoop$field_map.put("artist_prior_tracks_count", this.artist_prior_tracks_count);
    __sqoop$field_map.put("artist_avg_past_streams", this.artist_avg_past_streams);
    __sqoop$field_map.put("streams_in_the_first_month", this.streams_in_the_first_month);
  }

  public void setField(String __fieldName, Object __fieldVal) {
    if (!setters.containsKey(__fieldName)) {
      throw new RuntimeException("No such field:"+__fieldName);
    }
    setters.get(__fieldName).setField(__fieldVal);
  }

}
