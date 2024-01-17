import {Student} from "../studenti/student.model";

export interface Dokument {
  id?: number;
  url?: string;
  naziv: string;
  student: Student | null;
}
