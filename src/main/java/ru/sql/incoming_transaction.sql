select ty.name, sum(tr.amount)
from users as us
         join type as ty on us.id = ty.user_id
         join type_transaction as tt on ty.id = tt.type_id
         join transaction as tr on tt.transaction_id = tr.id
         join account as ac on tr.to_account_id = ac.id
where us.id = ? and tr.date > ? and tr.date < ?
group by ty.name