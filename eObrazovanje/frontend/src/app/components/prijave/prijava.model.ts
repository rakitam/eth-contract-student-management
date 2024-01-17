import {Ispit} from "../ispiti/ispit.model";
import {IspitniRok} from "../ispitni-rokovi/ispitni-rok.model";
import {Student} from "../studenti/student.model";

export interface Prijava {
  id?: number;
  datumPrijave: string | Date;
  student: Student | null;
  ispit?: Ispit;
  ispitniRok: IspitniRok | null;
}
