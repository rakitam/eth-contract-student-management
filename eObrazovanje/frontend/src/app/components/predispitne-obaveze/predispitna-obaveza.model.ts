import {Predmet} from "../predmet/predmet.model";

export interface PredispitnaObaveza {
  id?: number;
  naziv: string;
  minBodova: number;
  predmet: Predmet | null;
  tip: string;
}
