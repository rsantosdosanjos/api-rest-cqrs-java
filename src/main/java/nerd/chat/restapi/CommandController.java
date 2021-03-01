package nerd.chat.restapi;

import nerd.chat.coreapi.CreateRoomCommand;
import nerd.chat.coreapi.JoinRoomCommand;
import nerd.chat.coreapi.LeaveRoomCommand;
import nerd.chat.coreapi.PostMessageCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;
import java.util.concurrent.Future;

@RestController
public class CommandController {

  private final CommandGateway commandGateway;

  public CommandController(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PostMapping("/rooms")
  public Future<String> createChatRoom(@RequestBody @Valid Room room) {
    Assert.notNull(room.getName(), "name is mandatory for chatroom");
    String roomId = room.getRoomId() == null ? UUID.randomUUID().toString() : room.getRoomId();
    return commandGateway.send(new CreateRoomCommand(roomId, room.getName()));
  }

  @PostMapping("/rooms/{roomId}/participants")
  public Future<Void> joinChatRoom(@PathVariable String roomId, @RequestBody @Valid Participant participant) {
    Assert.notNull(participant.getName(), "name is mandatory for a participant");
    return commandGateway.send(new JoinRoomCommand(roomId, participant.getName()));
  }

  @PostMapping("/rooms/{roomId}/messages")
  public Future<Void> postMessage(@PathVariable String roomId, @RequestBody @Valid Message message) {
    Assert.notNull(message.getName(), "please provide a participant name");
    Assert.notNull(message.getMessage(), "please provide a message to post");
    return commandGateway.send(new PostMessageCommand(roomId, message.getName(), message.getMessage()));
  }

  public Future<Void> leaveChatRoom(@PathVariable String roomId, @RequestBody @Valid Participant participant) {
    Assert.notNull(participant.getName(), "name is mandatory to left room");
    return commandGateway.send(new LeaveRoomCommand(roomId, participant.getName()));
  }

  public static class Room {
    private String roomId;
    @NotEmpty
    private String name;

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
  }

  public static class Participant {
    @NotEmpty
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
  }

  public static class Message {
    @NotEmpty
    private String name;
    @NotEmpty
    private String message;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
  }
}
