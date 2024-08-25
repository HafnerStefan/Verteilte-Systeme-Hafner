
-- Insert the 'User' role into the roles table
INSERT INTO roles (id, name) VALUES (1, 'User');
INSERT INTO roles (id, name) VALUES (2, 'Admin');

-- Insert example users
INSERT INTO user (id, name, age, email, password, address, phone, gender, date_of_birth, created_at, updated_at)
VALUES (1, 'John Doe', 30, 'john.doe@example.com', 'Password123!', '123 Main St', '+41 78 965 26 15', 'male',
        '1992-01-01', '2023-01-01T12:00:00', '2023-01-01T12:00:00');

INSERT INTO user (id, name, age, email, password, address, phone, gender, date_of_birth, created_at, updated_at)
VALUES (2, 'Jane Doe', 28, 'jane.doe@example.com', 'Password123!', '456 Main St', '+41 79 123 45 67', 'female',
        '1994-02-02', '2023-01-01T12:00:00', '2023-01-01T12:00:00');

INSERT INTO user (id, name, age, email, password, address, phone, gender, date_of_birth, created_at, updated_at)
VALUES (3, 'Alice Smith', 25, 'alice.smith@example.com', 'Password123!', '789 Elm St', '+41 77 234 56 78', 'female',
        '1997-03-03', '2023-01-01T12:00:00', '2023-01-01T12:00:00');

INSERT INTO user (id, name, age, email, password, address, phone, gender, date_of_birth, created_at, updated_at)
VALUES (4, 'Bob Brown', 35, 'bob.brown@example.com', 'Password123!', '101 Oak St', '+41 76 345 67 89', 'male',
        '1987-04-04', '2023-01-01T12:00:00', '2023-01-01T12:00:00');

INSERT INTO user (id, name, age, email, password, address, phone, gender, date_of_birth, created_at, updated_at)
VALUES (5, 'Charlie Green', 40, 'charlie.green@example.com', 'Password123!', '202 Pine St', '+41 75 456 78 90', 'male',
        '1982-05-05', '2023-01-01T12:00:00', '2023-01-01T12:00:00');

INSERT INTO user (id, name, age, email, password, address, phone, gender, date_of_birth, created_at, updated_at)
VALUES (6, 'David White', 22, 'david.white@example.com', 'Password123!', '303 Birch St', '+41 74 567 89 01', 'male',
        '2000-06-06', '2023-01-01T12:00:00', '2023-01-01T12:00:00');

INSERT INTO user (id, name, age, email, password, address, phone, gender, date_of_birth, created_at, updated_at)
VALUES (7, 'Emma Black', 32, 'emma.black@example.com', 'Password123!', '404 Cedar St', '+41 73 678 90 12', 'female',
        '1990-07-07', '2023-01-01T12:00:00', '2023-01-01T12:00:00');

INSERT INTO user (id, name, age, email, password, address, phone, gender, date_of_birth, created_at, updated_at)
VALUES (8, 'Fiona Blue', 29, 'fiona.blue@example.com', 'Password123!', '505 Spruce St', '+41 72 789 01 23', 'female',
        '1993-08-08', '2023-01-01T12:00:00', '2023-01-01T12:00:00');

INSERT INTO user (id, name, age, email, password, address, phone, gender, date_of_birth, created_at, updated_at)
VALUES (9, 'George Red', 27, 'george.red@example.com', 'Password123!', '606 Willow St', '+41 71 890 12 34', 'male',
        '1995-09-09', '2023-01-01T12:00:00', '2023-01-01T12:00:00');

INSERT INTO user (id, name, age, email, password, address, phone, gender, date_of_birth, created_at, updated_at)
VALUES (10, 'Stefan Hafner', 31, 'Stefan.hafner@example.com', 'Password123!', '707 Maple St', '+41 70 901 23 45',
        'female', '1991-10-10', '2023-01-01T12:00:00', '2023-01-01T12:00:00');

-- Assign the 'User' role to all users
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (5, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (6, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (7, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (8, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (9, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (10, 2);

-- Insert example blogs
INSERT INTO blog (id, title, text, created_at, updated_at, user_id)
VALUES (1, 'First Blog', 'This is the content of the first blog.', '2023-01-01T12:00:00', '2023-01-01T12:00:00', 1);

INSERT INTO blog (id, title, text, created_at, updated_at, user_id)
VALUES (2, 'Second Blog', 'This is the content of the second blog.', '2023-01-02T12:00:00', '2023-01-02T12:00:00', 2);

INSERT INTO blog (id, title, text, created_at, updated_at, user_id)
VALUES (3, 'Third Blog', 'This is the content of the third blog.', '2023-01-03T12:00:00', '2023-01-03T12:00:00', 3);

INSERT INTO blog (id, title, text, created_at, updated_at, user_id)
VALUES (4, 'Fourth Blog', 'This is the content of the fourth blog.', '2023-01-04T12:00:00', '2023-01-04T12:00:00', 4);

INSERT INTO blog (id, title, text, created_at, updated_at, user_id)
VALUES (5, 'Fifth Blog', 'This is the content of the fifth blog.', '2023-01-05T12:00:00', '2023-01-05T12:00:00', 5);

INSERT INTO blog (id, title, text, created_at, updated_at, user_id)
VALUES (6, 'How to Cook the Perfect Steak', 'Cooking the perfect steak requires some practice. Here are some tips...',
        '2023-02-01T12:00:00', '2023-02-01T12:00:00', 6);

INSERT INTO blog (id, title, text, created_at, updated_at, user_id)
VALUES (7, 'Top 10 Travel Destinations for 2023',
        'Looking for your next travel adventure? Here are the top 10 destinations...', '2023-02-02T12:00:00',
        '2023-02-02T12:00:00', 7);

INSERT INTO blog (id, title, text, created_at, updated_at, user_id)
VALUES (8, 'The Benefits of Meditation',
        'Meditation can bring numerous benefits to your life. Lets explore some of them...', '2023-02-03T12:00:00',
        '2023-02-03T12:00:00', 8);

INSERT INTO blog (id, title, text, created_at, updated_at, user_id)
VALUES (9, 'A Guide to Plant-Based Eating',
        'Thinking about switching to a plant-based diet? Heres what you need to know...', '2023-02-04T12:00:00',
        '2023-02-04T12:00:00', 9);

INSERT INTO blog (id, title, text, created_at, updated_at, user_id)
VALUES (10, 'Tips for Improving Your Work-Life Balance',
        'Finding a good work-life balance can be challenging. Here are some tips...', '2023-02-05T12:00:00',
        '2023-02-05T12:00:00', 10);

-- Insert example comments
INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (1, 'Great post!', '2023-01-02T12:00:00', 1, 2);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (2, 'Interesting read.', '2023-01-02T13:00:00', 1, 3);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (3, 'I enjoyed this.', '2023-01-03T14:00:00', 2, 1);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (4, 'Very informative.', '2023-01-03T15:00:00', 2, 4);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (5, 'Thanks for sharing.', '2023-01-04T16:00:00', 3, 5);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (6, 'Well written.', '2023-01-04T17:00:00', 3, 1);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (7, 'Good job.', '2023-01-05T18:00:00', 4, 2);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (8, 'Nice article.', '2023-01-05T19:00:00', 4, 3);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (9, 'Very helpful.', '2023-01-06T20:00:00', 5, 4);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (10, 'Excellent work.', '2023-01-06T21:00:00', 5, 5);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (11, 'I tried this recipe and it was fantastic!', '2023-02-01T12:30:00', 6, 1);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (12, 'Cant wait to try this!', '2023-02-01T13:00:00', 6, 2);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (13, 'I love these travel tips.', '2023-02-02T14:00:00', 7, 3);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (14, 'This is very useful, thank you!', '2023-02-02T15:00:00', 7, 4);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (15, 'Meditation has changed my life.', '2023-02-03T16:00:00', 8, 5);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (16, 'Great insights on meditation.', '2023-02-03T17:00:00', 8, 6);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (17, 'Plant-based eating is the way to go.', '2023-02-04T18:00:00', 9, 7);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (18, 'Thanks for the guide!', '2023-02-04T19:00:00', 9, 8);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (19, 'These tips really helped me.', '2023-02-05T20:00:00', 10, 9);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (20, 'Excellent advice on work-life balance.', '2023-02-05T21:00:00', 10, 10);

-- Insert additional example comments
INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (21, 'This is exactly what I was looking for!', '2023-02-06T12:00:00', 1, 3);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (22, 'I have a question about this...', '2023-02-06T12:30:00', 2, 4);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (23, 'Can you provide more details?', '2023-02-06T13:00:00', 3, 5);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (24, 'I completely agree with this.', '2023-02-06T13:30:00', 4, 6);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (25, 'This was very helpful, thanks!', '2023-02-06T14:00:00', 5, 7);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (26, 'I learned a lot from this post.', '2023-02-06T14:30:00', 6, 8);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (27, 'Ive shared this with my friends.', '2023-02-06T15:00:00', 7, 9);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (28, 'What a fantastic article!', '2023-02-06T15:30:00', 8, 10);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (29, 'This really resonates with me.', '2023-02-06T16:00:00', 9, 1);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (30, 'Ill definitely try this out.', '2023-02-06T16:30:00', 10, 2);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (31, 'Such a well-written piece.', '2023-02-06T17:00:00', 1, 3);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (32, 'Thank you for sharing this.', '2023-02-06T17:30:00', 2, 4);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (33, 'I appreciate your perspective.', '2023-02-06T18:00:00', 3, 5);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (34, 'This was very insightful.', '2023-02-06T18:30:00', 4, 6);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (35, 'Can you write more about this?', '2023-02-06T19:00:00', 5, 7);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (36, 'Ive bookmarked this page.', '2023-02-06T19:30:00', 6, 8);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (37, 'This is a game changer.', '2023-02-06T20:00:00', 7, 9);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (38, 'Absolutely brilliant!', '2023-02-06T20:30:00', 8, 10);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (39, 'I couldnt agree more.', '2023-02-06T21:00:00', 9, 1);

INSERT INTO comment (id, text, created_at, blog_id, user_id)
VALUES (40, 'This is very well explained.', '2023-02-06T21:30:00', 10, 2);
