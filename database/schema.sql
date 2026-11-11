--
-- PostgreSQL database dump
--




SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 24590)
-- Name: coleta; Type: TABLE; Schema: public--

CREATE TABLE public.coleta (
    id integer NOT NULL,
    usuario_id integer NOT NULL,
    material_id integer NOT NULL,
    peso_kg numeric(10,2) NOT NULL,
    data_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    pontos_gerados integer DEFAULT 0 NOT NULL,
    CONSTRAINT coleta_peso_kg_check CHECK ((peso_kg > (0)::numeric)),
    CONSTRAINT coleta_pontos_gerados_check CHECK ((pontos_gerados >= 0))
);



--
-- TOC entry 220 (class 1259 OID 24603)
-- Name: coleta_id_seq; Type: SEQUENCE; Schema: public--

ALTER TABLE public.coleta ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.coleta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 221 (class 1259 OID 24604)
-- Name: comprovante; Type: TABLE; Schema: public--

CREATE TABLE public.comprovante (
    id integer NOT NULL,
    coleta_id integer NOT NULL,
    data_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    peso_total_kg numeric(10,2) NOT NULL,
    pontos integer DEFAULT 0 NOT NULL,
    usuario_id integer NOT NULL,
    detalhes text,
    CONSTRAINT comprovante_peso_total_kg_check CHECK ((peso_total_kg >= (0)::numeric)),
    CONSTRAINT comprovante_pontos_check CHECK ((pontos >= 0))
);



--
-- TOC entry 222 (class 1259 OID 24619)
-- Name: comprovante_id_seq; Type: SEQUENCE; Schema: public--

ALTER TABLE public.comprovante ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.comprovante_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 223 (class 1259 OID 24620)
-- Name: forma_pagamento; Type: TABLE; Schema: public--

CREATE TABLE public.forma_pagamento (
    id integer NOT NULL,
    descricao character varying(30) NOT NULL
);



--
-- TOC entry 224 (class 1259 OID 24625)
-- Name: forma_pagamento_id_seq; Type: SEQUENCE; Schema: public--

CREATE SEQUENCE public.forma_pagamento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- TOC entry 3960 (class 0 OID 0)
-- Dependencies: 224
-- Name: forma_pagamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public--

ALTER SEQUENCE public.forma_pagamento_id_seq OWNED BY public.forma_pagamento.id;


--
-- TOC entry 225 (class 1259 OID 24626)
-- Name: gastos; Type: TABLE; Schema: public--

CREATE TABLE public.gastos (
    id integer NOT NULL,
    descricao character varying(150) NOT NULL,
    valor numeric(10,2) NOT NULL,
    categoria character varying(50) NOT NULL,
    status_id integer NOT NULL,
    forma_pagamento_id integer NOT NULL,
    criado_em timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT gastos_valor_check CHECK ((valor > (0)::numeric))
);



--
-- TOC entry 226 (class 1259 OID 24638)
-- Name: gastos_id_seq; Type: SEQUENCE; Schema: public--

CREATE SEQUENCE public.gastos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- TOC entry 3961 (class 0 OID 0)
-- Dependencies: 226
-- Name: gastos_id_seq; Type: SEQUENCE OWNED BY; Schema: public--

ALTER SEQUENCE public.gastos_id_seq OWNED BY public.gastos.id;


--
-- TOC entry 227 (class 1259 OID 24639)
-- Name: material; Type: TABLE; Schema: public--

CREATE TABLE public.material (
    id integer NOT NULL,
    nome character varying(50) NOT NULL,
    tipo_material_id integer NOT NULL,
    pontos_por_kg numeric(6,2) DEFAULT 0 NOT NULL,
    ativo boolean DEFAULT true NOT NULL,
    CONSTRAINT material_pontos_por_kg_check CHECK ((pontos_por_kg >= (0)::numeric))
);



--
-- TOC entry 228 (class 1259 OID 24650)
-- Name: material_id_seq; Type: SEQUENCE; Schema: public--

ALTER TABLE public.material ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.material_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 229 (class 1259 OID 24651)
-- Name: ponto_usuario; Type: TABLE; Schema: public--

CREATE TABLE public.ponto_usuario (
    id integer NOT NULL,
    usuario_id integer NOT NULL,
    pontos integer NOT NULL,
    data_atualizacao timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    motivo character varying(50) NOT NULL,
    CONSTRAINT chk_motivo_pontos CHECK (((motivo)::text = ANY (ARRAY[('COLETA'::character varying)::text, ('RESGATE'::character varying)::text, ('AJUSTE'::character varying)::text, ('ESTORNO'::character varying)::text]))),
    CONSTRAINT chk_pontos_nao_zero CHECK ((pontos <> 0))
);



--
-- TOC entry 230 (class 1259 OID 24662)
-- Name: ponto_usuario_id_seq; Type: SEQUENCE; Schema: public--

ALTER TABLE public.ponto_usuario ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.ponto_usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 231 (class 1259 OID 24663)
-- Name: resgate_pontos; Type: TABLE; Schema: public--

CREATE TABLE public.resgate_pontos (
    id integer NOT NULL,
    usuario_id integer NOT NULL,
    pontos_utilizados integer NOT NULL,
    data_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    descricao character varying(255),
    status character varying(20) DEFAULT 'PENDENTE'::character varying NOT NULL,
    CONSTRAINT chk_status_resgate CHECK (((status)::text = ANY (ARRAY[('PENDENTE'::character varying)::text, ('APROVADO'::character varying)::text, ('CANCELADO'::character varying)::text]))),
    CONSTRAINT resgate_pontos_pontos_utilizados_check CHECK ((pontos_utilizados > 0))
);



--
-- TOC entry 232 (class 1259 OID 24675)
-- Name: resgate_pontos_id_seq; Type: SEQUENCE; Schema: public--

ALTER TABLE public.resgate_pontos ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.resgate_pontos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 233 (class 1259 OID 24676)
-- Name: status; Type: TABLE; Schema: public--

CREATE TABLE public.status (
    id integer NOT NULL,
    descricao character varying(20) NOT NULL
);



--
-- TOC entry 234 (class 1259 OID 24681)
-- Name: status_id_seq; Type: SEQUENCE; Schema: public--

CREATE SEQUENCE public.status_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- TOC entry 3962 (class 0 OID 0)
-- Dependencies: 234
-- Name: status_id_seq; Type: SEQUENCE OWNED BY; Schema: public--

ALTER SEQUENCE public.status_id_seq OWNED BY public.status.id;


--
-- TOC entry 235 (class 1259 OID 24682)
-- Name: tipo_material; Type: TABLE; Schema: public--

CREATE TABLE public.tipo_material (
    id integer NOT NULL,
    nome character varying(50) NOT NULL,
    descricao text,
    ativo boolean DEFAULT true NOT NULL
);



--
-- TOC entry 236 (class 1259 OID 24691)
-- Name: tipo_material_id_seq; Type: SEQUENCE; Schema: public--

ALTER TABLE public.tipo_material ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tipo_material_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 237 (class 1259 OID 24692)
-- Name: usuario; Type: TABLE; Schema: public--

CREATE TABLE public.usuario (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    cpf character varying(14) NOT NULL,
    email character varying(100) NOT NULL,
    senha_hash character varying(255) NOT NULL,
    endereco character varying(255),
    telefone character varying(20),
    data_cadastro timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);



--
-- TOC entry 238 (class 1259 OID 24704)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public--

ALTER TABLE public.usuario ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3744 (class 2604 OID 24705)
-- Name: forma_pagamento id; Type: DEFAULT; Schema: public--

ALTER TABLE ONLY public.forma_pagamento ALTER COLUMN id SET DEFAULT nextval('public.forma_pagamento_id_seq'::regclass);


--
-- TOC entry 3745 (class 2604 OID 24706)
-- Name: gastos id; Type: DEFAULT; Schema: public--

ALTER TABLE ONLY public.gastos ALTER COLUMN id SET DEFAULT nextval('public.gastos_id_seq'::regclass);


--
-- TOC entry 3752 (class 2604 OID 24707)
-- Name: status id; Type: DEFAULT; Schema: public--

ALTER TABLE ONLY public.status ALTER COLUMN id SET DEFAULT nextval('public.status_id_seq'::regclass);


--
-- TOC entry 3766 (class 2606 OID 24709)
-- Name: coleta coleta_pkey; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.coleta
    ADD CONSTRAINT coleta_pkey PRIMARY KEY (id);


--
-- TOC entry 3768 (class 2606 OID 24711)
-- Name: comprovante comprovante_coleta_id_key; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.comprovante
    ADD CONSTRAINT comprovante_coleta_id_key UNIQUE (coleta_id);


--
-- TOC entry 3770 (class 2606 OID 24713)
-- Name: comprovante comprovante_pkey; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.comprovante
    ADD CONSTRAINT comprovante_pkey PRIMARY KEY (id);


--
-- TOC entry 3772 (class 2606 OID 24715)
-- Name: forma_pagamento forma_pagamento_descricao_key; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_descricao_key UNIQUE (descricao);


--
-- TOC entry 3774 (class 2606 OID 24717)
-- Name: forma_pagamento forma_pagamento_pkey; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id);


--
-- TOC entry 3776 (class 2606 OID 24719)
-- Name: gastos gastos_pkey; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.gastos
    ADD CONSTRAINT gastos_pkey PRIMARY KEY (id);


--
-- TOC entry 3778 (class 2606 OID 24721)
-- Name: material material_nome_key; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.material
    ADD CONSTRAINT material_nome_key UNIQUE (nome);


--
-- TOC entry 3780 (class 2606 OID 24723)
-- Name: material material_pkey; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.material
    ADD CONSTRAINT material_pkey PRIMARY KEY (id);


--
-- TOC entry 3782 (class 2606 OID 24725)
-- Name: ponto_usuario ponto_usuario_pkey; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.ponto_usuario
    ADD CONSTRAINT ponto_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3784 (class 2606 OID 24727)
-- Name: resgate_pontos resgate_pontos_pkey; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.resgate_pontos
    ADD CONSTRAINT resgate_pontos_pkey PRIMARY KEY (id);


--
-- TOC entry 3786 (class 2606 OID 24729)
-- Name: status status_descricao_key; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.status
    ADD CONSTRAINT status_descricao_key UNIQUE (descricao);


--
-- TOC entry 3788 (class 2606 OID 24731)
-- Name: status status_pkey; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.status
    ADD CONSTRAINT status_pkey PRIMARY KEY (id);


--
-- TOC entry 3790 (class 2606 OID 24733)
-- Name: tipo_material tipo_material_nome_key; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.tipo_material
    ADD CONSTRAINT tipo_material_nome_key UNIQUE (nome);


--
-- TOC entry 3792 (class 2606 OID 24735)
-- Name: tipo_material tipo_material_pkey; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.tipo_material
    ADD CONSTRAINT tipo_material_pkey PRIMARY KEY (id);


--
-- TOC entry 3794 (class 2606 OID 24737)
-- Name: usuario usuario_cpf_key; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_cpf_key UNIQUE (cpf);


--
-- TOC entry 3796 (class 2606 OID 24739)
-- Name: usuario usuario_email_key; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);


--
-- TOC entry 3798 (class 2606 OID 24741)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3799 (class 2606 OID 24742)
-- Name: coleta fk_coleta_material; Type: FK CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.coleta
    ADD CONSTRAINT fk_coleta_material FOREIGN KEY (material_id) REFERENCES public.material(id);


--
-- TOC entry 3800 (class 2606 OID 24747)
-- Name: coleta fk_coleta_usuario; Type: FK CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.coleta
    ADD CONSTRAINT fk_coleta_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- TOC entry 3801 (class 2606 OID 24752)
-- Name: comprovante fk_comprovante_coleta; Type: FK CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.comprovante
    ADD CONSTRAINT fk_comprovante_coleta FOREIGN KEY (coleta_id) REFERENCES public.coleta(id);


--
-- TOC entry 3802 (class 2606 OID 24757)
-- Name: comprovante fk_comprovante_usuario; Type: FK CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.comprovante
    ADD CONSTRAINT fk_comprovante_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- TOC entry 3805 (class 2606 OID 24762)
-- Name: material fk_material_tipo; Type: FK CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.material
    ADD CONSTRAINT fk_material_tipo FOREIGN KEY (tipo_material_id) REFERENCES public.tipo_material(id);


--
-- TOC entry 3806 (class 2606 OID 24767)
-- Name: ponto_usuario fk_ponto_usuario; Type: FK CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.ponto_usuario
    ADD CONSTRAINT fk_ponto_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- TOC entry 3807 (class 2606 OID 24772)
-- Name: resgate_pontos fk_resgate_usuario; Type: FK CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.resgate_pontos
    ADD CONSTRAINT fk_resgate_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- TOC entry 3803 (class 2606 OID 24777)
-- Name: gastos gastos_forma_pagamento_id_fkey; Type: FK CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.gastos
    ADD CONSTRAINT gastos_forma_pagamento_id_fkey FOREIGN KEY (forma_pagamento_id) REFERENCES public.forma_pagamento(id);


--
-- TOC entry 3804 (class 2606 OID 24782)
-- Name: gastos gastos_status_id_fkey; Type: FK CONSTRAINT; Schema: public--

ALTER TABLE ONLY public.gastos
    ADD CONSTRAINT gastos_status_id_fkey FOREIGN KEY (status_id) REFERENCES public.status(id);



--
-- PostgreSQL database dump complete
--


