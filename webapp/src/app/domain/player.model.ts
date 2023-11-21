import {EMPTY_PLAYED_CARD, PlayedCardModel} from "./played-card.model";

export interface PlayerModel {
  name: string,
  playedCard: PlayedCardModel;
}

export const EMPTY_PLAYER: PlayerModel = {
  name: "EMPTY_PLAYER",
  playedCard: EMPTY_PLAYED_CARD
};
