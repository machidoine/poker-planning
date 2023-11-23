import {PlayerModel} from "./player.model";

export interface RoomModel {
  id: string,
  cardRevealed: boolean,
  players: PlayerModel[]
}

export const EMPTY_ROOM: RoomModel = {
  id: "empty",
  cardRevealed: false,
  players: []
};
