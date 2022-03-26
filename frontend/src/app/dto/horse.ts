/**
 * Class for Horse DTOs.
 * Contains all common properties.
 * Owner and Parents are represented by IDs
 */
export interface Horse {
  id?: bigint;
  name: string;
  description?: string;
  dateOfBirth: Date;
  sex: Sex;
  ownerId?: bigint;
  fatherId?: bigint;
  motherId?: bigint;
}

type Sex = "male" | "female";
