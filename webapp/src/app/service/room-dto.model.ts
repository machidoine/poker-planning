import {PlayerDtoModel} from "./player-dto.model";

export interface RoomDtoModel {
  "id": string,
  "players": PlayerDtoModel[]
  "cardRevealed": boolean
}


// {"id":"12","players":[{"name":"ben","card":null,"hasPlayed":false,"publicId":"692007df-bb23-4791-b3e1-621f2f104a88"}],"cardRevealed":false}
