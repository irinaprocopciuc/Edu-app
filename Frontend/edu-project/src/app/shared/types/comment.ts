export interface Comment {
  commentId: string;
  date: string;
  message: string;
  userId: string;
  username: string;
  isEditMode?: boolean;
}
