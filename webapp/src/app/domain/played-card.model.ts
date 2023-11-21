export interface PlayedCardModel {
  value?: number,
  isRecto: boolean;
}

export const EMPTY_PLAYED_CARD: PlayedCardModel = {
  value: undefined,
  isRecto: true
};
