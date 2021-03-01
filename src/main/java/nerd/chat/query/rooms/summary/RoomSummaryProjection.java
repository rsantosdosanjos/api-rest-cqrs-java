package nerd.chat.query.rooms.summary;

import nerd.chat.coreapi.AllRoomsQuery;
import nerd.chat.coreapi.ParticipantJoinedRoomEvent;
import nerd.chat.coreapi.ParticipantLeftRoomEvent;
import nerd.chat.coreapi.RoomCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomSummaryProjection {

  private final RoomSummaryRepository roomSummaryRepository;

  public RoomSummaryProjection(RoomSummaryRepository roomSummaryRepository) {
    this.roomSummaryRepository = roomSummaryRepository;
  }

  @QueryHandler
  public List<RoomSummary> handle(AllRoomsQuery query) { return roomSummaryRepository.findAll(); }

  @EventHandler
  public void on(RoomCreatedEvent event) {
    roomSummaryRepository.save(new RoomSummary(event.getRoomId(), event.getName()));
  }

  @EventHandler
  public void on(ParticipantJoinedRoomEvent event) {
    roomSummaryRepository.getOne(event.getRoomId()).addParticipant();
  }

  @EventHandler
  public void on(ParticipantLeftRoomEvent event) {
    roomSummaryRepository.getOne(event.getRoomId()).removeParticipant();
  }
}
