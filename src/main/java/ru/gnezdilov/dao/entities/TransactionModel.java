    package ru.gnezdilov.dao.entities;

    import lombok.EqualsAndHashCode;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import ru.gnezdilov.service.custominterface.HasId;

    import javax.persistence.*;
    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Table(name = "transaction")
    @Getter
    @Setter
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @NoArgsConstructor
    public class TransactionModel implements HasId {
        public TransactionModel(int id, int senderAccountId, int receiverAccountId, BigDecimal amount, LocalDate date) {
            this.id = id;
            this.senderAccountId = senderAccountId;
            this.receiverAccountId = receiverAccountId;
            this.amount = amount;
            this.date = date;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        private int id;

        @Column(name = "from_account_id")
        @EqualsAndHashCode.Include
        private int senderAccountId;

        @Column(name = "to_account_id")
        @EqualsAndHashCode.Include
        private int receiverAccountId;

        @Column(nullable = false, name = "amount")
        @EqualsAndHashCode.Include
        private BigDecimal amount;

        @Column(nullable = false, name = "date")
        @EqualsAndHashCode.Include
        private LocalDate date;

        @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
        private Set<CategoryTransactionModel> categoryTransactionModel= new HashSet<>();

        @Override
        public String toString() {
            return id + ". " + date +": " + amount;
        }

        public void linkTypeId(int typeId) {
            CategoryTransactionModel ctm = new CategoryTransactionModel();
            ctm.setTypeId(typeId);
            ctm.setTransaction(this);
            this.categoryTransactionModel.add(ctm);
        }
    }
