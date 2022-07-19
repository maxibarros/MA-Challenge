package com.example.machallenge.models;

public enum OrderState {
    CREATED {
        public String toString() {
            return "CREADO";
        }
    },
    PENDING {
        public String toString() {
            return "PENDIENTE";
        }
    },
    DELIVERED {
        public String toString() {
            return "ENTREGADO";
        }
    },
    INVALID {
        public String toString() {
            return "INVALIDO";
        }
    },
    CANCELLED {
        public String toString() {
            return "CANCELADO";
        }
    };
}
