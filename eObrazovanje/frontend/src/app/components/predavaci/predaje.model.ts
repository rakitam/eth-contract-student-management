import {Predmet} from "../predmet/predmet.model";
import {Nastavnik} from "../nastavnici/nastavnik.model";
import {Zvanje} from "../zvanja/zvanje.model";

export interface Predaje {
  id?: number;
  predmet: Predmet | null;
  nastavnik: Nastavnik | null;
  zvanje: Zvanje | null;
}
