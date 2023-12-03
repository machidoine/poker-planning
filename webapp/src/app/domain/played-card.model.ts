export interface PlayedCardModel {
  value?: string,
  isRecto: boolean,
  played: boolean
}

export const EMPTY_PLAYED_CARD: PlayedCardModel = {
  value: undefined,
  isRecto: true,
  played: false
};
