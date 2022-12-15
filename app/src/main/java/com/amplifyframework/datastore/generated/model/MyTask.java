package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the MyTask type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "MyTasks", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byTeams", fields = {"TeamId","title"})
public final class MyTask implements Model {
  public static final QueryField ID = field("MyTask", "id");
  public static final QueryField TITLE = field("MyTask", "title");
  public static final QueryField BODY = field("MyTask", "body");
  public static final QueryField STATE = field("MyTask", "state");
  public static final QueryField DATE_POSTED = field("MyTask", "datePosted");
  public static final QueryField TEAM = field("MyTask", "TeamId");
  public static final QueryField S3_IMAGE_KEY = field("MyTask", "s3ImageKey");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String") String body;
  private final @ModelField(targetType="TaskStateEnum") TaskStateEnum state;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime datePosted;
  private final @ModelField(targetType="Team") @BelongsTo(targetName = "TeamId", targetNames = {"TeamId"}, type = Team.class) Team team;
  private final @ModelField(targetType="String") String s3ImageKey;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getBody() {
      return body;
  }
  
  public TaskStateEnum getState() {
      return state;
  }
  
  public Temporal.DateTime getDatePosted() {
      return datePosted;
  }
  
  public Team getTeam() {
      return team;
  }
  
  public String getS3ImageKey() {
      return s3ImageKey;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private MyTask(String id, String title, String body, TaskStateEnum state, Temporal.DateTime datePosted, Team team, String s3ImageKey) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.state = state;
    this.datePosted = datePosted;
    this.team = team;
    this.s3ImageKey = s3ImageKey;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      MyTask myTask = (MyTask) obj;
      return ObjectsCompat.equals(getId(), myTask.getId()) &&
              ObjectsCompat.equals(getTitle(), myTask.getTitle()) &&
              ObjectsCompat.equals(getBody(), myTask.getBody()) &&
              ObjectsCompat.equals(getState(), myTask.getState()) &&
              ObjectsCompat.equals(getDatePosted(), myTask.getDatePosted()) &&
              ObjectsCompat.equals(getTeam(), myTask.getTeam()) &&
              ObjectsCompat.equals(getS3ImageKey(), myTask.getS3ImageKey()) &&
              ObjectsCompat.equals(getCreatedAt(), myTask.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), myTask.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getBody())
      .append(getState())
      .append(getDatePosted())
      .append(getTeam())
      .append(getS3ImageKey())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("MyTask {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("datePosted=" + String.valueOf(getDatePosted()) + ", ")
      .append("team=" + String.valueOf(getTeam()) + ", ")
      .append("s3ImageKey=" + String.valueOf(getS3ImageKey()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static MyTask justId(String id) {
    return new MyTask(
      id,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      body,
      state,
      datePosted,
      team,
      s3ImageKey);
  }
  public interface TitleStep {
    BuildStep title(String title);
  }
  

  public interface BuildStep {
    MyTask build();
    BuildStep id(String id);
    BuildStep body(String body);
    BuildStep state(TaskStateEnum state);
    BuildStep datePosted(Temporal.DateTime datePosted);
    BuildStep team(Team team);
    BuildStep s3ImageKey(String s3ImageKey);
  }
  

  public static class Builder implements TitleStep, BuildStep {
    private String id;
    private String title;
    private String body;
    private TaskStateEnum state;
    private Temporal.DateTime datePosted;
    private Team team;
    private String s3ImageKey;
    @Override
     public MyTask build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new MyTask(
          id,
          title,
          body,
          state,
          datePosted,
          team,
          s3ImageKey);
    }
    
    @Override
     public BuildStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep state(TaskStateEnum state) {
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep datePosted(Temporal.DateTime datePosted) {
        this.datePosted = datePosted;
        return this;
    }
    
    @Override
     public BuildStep team(Team team) {
        this.team = team;
        return this;
    }
    
    @Override
     public BuildStep s3ImageKey(String s3ImageKey) {
        this.s3ImageKey = s3ImageKey;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, String body, TaskStateEnum state, Temporal.DateTime datePosted, Team team, String s3ImageKey) {
      super.id(id);
      super.title(title)
        .body(body)
        .state(state)
        .datePosted(datePosted)
        .team(team)
        .s3ImageKey(s3ImageKey);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder state(TaskStateEnum state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder datePosted(Temporal.DateTime datePosted) {
      return (CopyOfBuilder) super.datePosted(datePosted);
    }
    
    @Override
     public CopyOfBuilder team(Team team) {
      return (CopyOfBuilder) super.team(team);
    }
    
    @Override
     public CopyOfBuilder s3ImageKey(String s3ImageKey) {
      return (CopyOfBuilder) super.s3ImageKey(s3ImageKey);
    }
  }
  
}
