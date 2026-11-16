CREATE TABLE IF NOT EXISTS public.resgate (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    usuario_id integer NOT NULL REFERENCES public.usuario(id),
    codigo_comprovante varchar(24) NOT NULL UNIQUE,
    recompensa_codigo varchar(64) NOT NULL,
    recompensa_nome varchar(180) NOT NULL,
    pontos_gastos integer NOT NULL CHECK (pontos_gastos > 0),
    data_hora timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_resgate_usuario_data
    ON public.resgate (usuario_id, data_hora DESC);
