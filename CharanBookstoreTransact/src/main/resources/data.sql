DELETE FROM book;
ALTER TABLE book AUTO_INCREMENT = 1001;

DELETE FROM category;
ALTER TABLE category AUTO_INCREMENT = 1001;

INSERT INTO `category` (`name`) VALUES ('Romance'),('Mystery'),('Fantasy'),('Philosophy');

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Gone With The Wind', 'Margaret Mitchell', 'Most Beloved Epic Novel', 912, 5, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Night Will Find You', 'Julia Heaberlin', '', 405, 4, FALSE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('My Fair Gentleman', 'Nancy Campbell Allen', 'A Proper Romance', 399, 3, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Summer House', 'Jenny Hale', 'A gorgeous feel good romance that will have you hooked', 199, 2, TRUE, TRUE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Alaska Home', 'Debbie Macomber', 'A Romance Novel', 405, 3, FALSE, FALSE, 1001);

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Girl Missing', 'Kate Gable', 'A gripping psychological mystery', 876, 5, TRUE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Intent', 'Tim Hoy', 'A Detective Inspector Tessa Grantley Mystery', 650, 3, TRUE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Path Of Bones', 'L.T.Ryan', 'A Cassie Quinn Mystery', 499, 3, TRUE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Girl Who Lived', 'Christopher Greyson', 'A Thrilling Suspense Novel', 299, 3, FALSE, TRUE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('If She Knew', 'Blake Pierce', 'A Kate Wise Mystery', 101, 2, FALSE, FALSE, 1002);

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Fairy Tale', 'Stephen King', '', 1199, 5, FALSE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Dragon Mage', 'ML Spencer', 'An Epic Fantasy Adventure', 875, 4, TRUE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Forbidden', 'Lori Holmes', 'A Fantasy Fiction Series', 500, 3, FALSE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Lords of Alekka', 'A.E.Rayne', 'An Epic Fantasy Adventure', 499, 2, TRUE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Fuse', 'E.L.Todd', 'An Epic High Fantasy Adventure', 85, 1, TRUE, TRUE, 1003);

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Atlas Shrugged', 'Ayn Rand', '', 18.99, 5, TRUE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Fountainhead', 'AynRand', '', 20.58, 4, TRUE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Wisdom Of Life', 'Schopenhauer', '', 15.29, 3, FALSE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Thus Spoke Zarathustra', 'Nietzsche', '', 12.99, 3, FALSE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Science of Mind', 'Ernest Holmes', 'A Philosophy, A Faith, A Way of Life', 10.9, 2, TRUE, TRUE, 1004);

UPDATE book SET is_public = FALSE WHERE title = 'Alaska Home';
