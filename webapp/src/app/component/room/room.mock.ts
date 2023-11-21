import {DeckModel} from "../../domain/deck.model";
import {RoomModel} from "../../domain/room.model";

export const ROOM_MOCK: RoomModel = {
  id: "roomId1",
  players: [
    {
      name: "8Verso",
      playedCard: {
        value: 5,
        isRecto: true
      }
    },
    {
      name: "8Recto",
      playedCard: {
        value: 8,
        isRecto: false
      }
    },
    {
      name: "nullRecto",
      playedCard: {
        value: undefined,
        isRecto: false
      }
    },
    {
      name: "nullVerso",
      playedCard: {
        value: undefined,
        isRecto: true
      }
    }
  ]
};
