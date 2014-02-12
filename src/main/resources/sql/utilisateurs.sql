-- Initialise la base de données en mode bouchonné --
INSERT INTO public.utilisateur (id, version, email, login, password) VALUES (2, 1, 'toto@gmail.com', 'toto', 'test');
INSERT INTO public.utilisateur (id, version, email, login, password) VALUES (1, 1, 'titi@gmail.com', 'titi', 'test');

INSERT INTO public.role (id, version, nom) VALUES (1, 1, 'ROLE_USER');
INSERT INTO public.role (id, version, nom) VALUES (2, 1, 'ROLE_ADMIN');

INSERT INTO public.utilisateur_role (utilisateur, role) VALUES (1, 1);
INSERT INTO public.utilisateur_role (utilisateur, role) VALUES (2, 2);
