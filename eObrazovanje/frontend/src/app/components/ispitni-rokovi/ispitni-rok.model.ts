import {Rok} from "../rokovi/rok.model";
import {Predaje} from "../predavaci/predaje.model";

export interface IspitniRok {
  id?: number;
  predaje: Predaje | null;
  rok: Rok | null;
  datumIspita: string;
}
