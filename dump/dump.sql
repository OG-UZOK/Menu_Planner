--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

-- Started on 2024-06-08 17:29:47 +07

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 16560)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 3673 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 16610)
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16583)
-- Name: dishes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dishes (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    name character varying(255) NOT NULL,
    date date NOT NULL,
    user_id uuid
);


ALTER TABLE public.dishes OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16645)
-- Name: dishes_categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dishes_categories (
    dish_id uuid,
    category_id uuid
);


ALTER TABLE public.dishes_categories OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16658)
-- Name: dishes_ingridients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dishes_ingridients (
    ingridient_id uuid,
    dish_id uuid,
    amount integer,
    unit character varying(255)
);


ALTER TABLE public.dishes_ingridients OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16632)
-- Name: dishes_tags; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dishes_tags (
    dish_id uuid,
    tag_id uuid
);


ALTER TABLE public.dishes_tags OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16604)
-- Name: ingridients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ingridients (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    is_liquid boolean,
    name character varying(255) NOT NULL
);


ALTER TABLE public.ingridients OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16622)
-- Name: steps; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.steps (
    dish_id uuid,
    number integer NOT NULL,
    title character varying(255) NOT NULL,
    image bytea,
    description text
);


ALTER TABLE public.steps OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16616)
-- Name: tags; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tags (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.tags OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16594)
-- Name: tokens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tokens (
    value character varying(255) NOT NULL,
    user_id uuid
);


ALTER TABLE public.tokens OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16553)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    name character varying(255) NOT NULL,
    surname character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    email character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 3662 (class 0 OID 16610)
-- Dependencies: 220
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3659 (class 0 OID 16583)
-- Dependencies: 217
-- Data for Name: dishes; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3666 (class 0 OID 16645)
-- Dependencies: 224
-- Data for Name: dishes_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3667 (class 0 OID 16658)
-- Dependencies: 225
-- Data for Name: dishes_ingridients; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3665 (class 0 OID 16632)
-- Dependencies: 223
-- Data for Name: dishes_tags; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3661 (class 0 OID 16604)
-- Dependencies: 219
-- Data for Name: ingridients; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3664 (class 0 OID 16622)
-- Dependencies: 222
-- Data for Name: steps; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3663 (class 0 OID 16616)
-- Dependencies: 221
-- Data for Name: tags; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3660 (class 0 OID 16594)
-- Dependencies: 218
-- Data for Name: tokens; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3658 (class 0 OID 16553)
-- Dependencies: 216
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (id, name, surname, password, email) VALUES ('6924ccb8-c2d2-48ca-8481-d621f231c95d', 'robert', 'jackson', '123456', 'gfdg');


--
-- TOC entry 3503 (class 2606 OID 16615)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 3497 (class 2606 OID 16588)
-- Name: dishes dishes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dishes
    ADD CONSTRAINT dishes_pkey PRIMARY KEY (id);


--
-- TOC entry 3501 (class 2606 OID 16609)
-- Name: ingridients ingridients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingridients
    ADD CONSTRAINT ingridients_pkey PRIMARY KEY (id);


--
-- TOC entry 3505 (class 2606 OID 16621)
-- Name: tags tags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tags
    ADD CONSTRAINT tags_pkey PRIMARY KEY (id);


--
-- TOC entry 3499 (class 2606 OID 16598)
-- Name: tokens tokens_value_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tokens
    ADD CONSTRAINT tokens_value_key UNIQUE (value);


--
-- TOC entry 3495 (class 2606 OID 16559)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3511 (class 2606 OID 16653)
-- Name: dishes_categories dishes_categories_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dishes_categories
    ADD CONSTRAINT dishes_categories_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- TOC entry 3512 (class 2606 OID 16648)
-- Name: dishes_categories dishes_categories_dish_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dishes_categories
    ADD CONSTRAINT dishes_categories_dish_id_fkey FOREIGN KEY (dish_id) REFERENCES public.dishes(id);


--
-- TOC entry 3513 (class 2606 OID 16661)
-- Name: dishes_ingridients dishes_ingridients_dish_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dishes_ingridients
    ADD CONSTRAINT dishes_ingridients_dish_id_fkey FOREIGN KEY (dish_id) REFERENCES public.dishes(id);


--
-- TOC entry 3514 (class 2606 OID 16666)
-- Name: dishes_ingridients dishes_ingridients_ingridient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dishes_ingridients
    ADD CONSTRAINT dishes_ingridients_ingridient_id_fkey FOREIGN KEY (ingridient_id) REFERENCES public.ingridients(id);


--
-- TOC entry 3509 (class 2606 OID 16635)
-- Name: dishes_tags dishes_tags_dish_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dishes_tags
    ADD CONSTRAINT dishes_tags_dish_id_fkey FOREIGN KEY (dish_id) REFERENCES public.dishes(id);


--
-- TOC entry 3510 (class 2606 OID 16640)
-- Name: dishes_tags dishes_tags_tag_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dishes_tags
    ADD CONSTRAINT dishes_tags_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.tags(id);


--
-- TOC entry 3506 (class 2606 OID 16589)
-- Name: dishes dishes_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dishes
    ADD CONSTRAINT dishes_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3508 (class 2606 OID 16627)
-- Name: steps steps_dish_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.steps
    ADD CONSTRAINT steps_dish_id_fkey FOREIGN KEY (dish_id) REFERENCES public.dishes(id);


--
-- TOC entry 3507 (class 2606 OID 16599)
-- Name: tokens tokens_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tokens
    ADD CONSTRAINT tokens_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


-- Completed on 2024-06-08 17:29:48 +07

--
-- PostgreSQL database dump complete
--

