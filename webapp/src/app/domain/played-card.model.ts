export interface PlayedCardModel {
  value?: number,
  isRecto: boolean,
  played: boolean
}

export const EMPTY_PLAYED_CARD: PlayedCardModel = {
  value: undefined,
  isRecto: true,
  played: false
};
