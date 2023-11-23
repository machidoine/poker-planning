import {RoomModel} from "../../domain/room.model";

export const ROOM_MOCK: RoomModel = {
  id: "roomId1",
  cardRevealed: false,
  players: [
    {
      name: "8Verso",
      playedCard: {
        value: 5,
        isRecto: true,
        played: false
      }
    },
    {
      name: "8Recto",
      playedCard: {
        value: 8,
        isRecto: false,
        played: true
      }
    },
    {
      name: "nullRecto",
      playedCard: {
        value: undefined,
        isRecto: false,
        played: true
      }
    },
    {
      name: "nullVerso",
      playedCard: {
        value: undefined,
        isRecto: true,
        played: false
      }
    }
  ]
};
