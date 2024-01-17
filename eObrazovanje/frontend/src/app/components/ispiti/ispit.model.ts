import {Predaje} from "../predavaci/predaje.model";
import {Student} from "../studenti/student.model";

export interface Ispit {
  id?: number;
  datum: Date | string;
  predaje: Predaje | null;
  student: Student | null;
  bodovi: number | null;
  ocena: number | null;
  konacno: boolean;
}
