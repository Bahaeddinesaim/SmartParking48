using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace parking.Models
{
    [Table("tb_reservation")]

    public class Reservation
    {
        [Key]
        public int id_reservation { get; set; }
        public string date_debut { get; set; }
        public string date_expiration { get; set; }
        [ForeignKey("Place")]
        public int id_place { get; set; }
        [ForeignKey("Occupation")]
        public string idproprio { get; set; }

        public Place Place { get; set; }
        public Occupation Occupation { get; set; }
    }
}
