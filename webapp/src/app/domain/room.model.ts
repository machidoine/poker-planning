import {PlayerModel} from "./player.model";

export interface RoomModel {
  id: string,
  players: PlayerModel[];
}

export const EMPTY_ROOM: RoomModel = {
  id: "empty",
  players: []
};
