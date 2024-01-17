import {Student} from "../studenti/student.model";

export interface Uplata {
  id?: number;
  datum: Date | string;
  iznos: number;
  student: Student | null;
  stornirano?: boolean;
}
