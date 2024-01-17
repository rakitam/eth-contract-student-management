import {Predmet} from "../predmet/predmet.model";
import {Student} from "../studenti/student.model";

export interface StudentPredmet {
  id?: number;
  predmet: Predmet | null;
  student: Student | null;
  polozio: Boolean;
  osvojeniBodovi: number;
}
